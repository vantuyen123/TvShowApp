package com.example.tvshowsapp.active;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.text.HtmlCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.tvshowsapp.R;
import com.example.tvshowsapp.adapters.EpisodesAdapter;
import com.example.tvshowsapp.adapters.ImageSliderAdapter;
import com.example.tvshowsapp.databinding.ActivityTVShowDetailsBinding;
import com.example.tvshowsapp.databinding.LayoutEpisodesBottomSheetBinding;
import com.example.tvshowsapp.models.TVShow;
import com.example.tvshowsapp.response.TVShowDetailsResponse;
import com.example.tvshowsapp.utillities.TempDataHolder;
import com.example.tvshowsapp.viewmodels.TVShowDetailsViewModel;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.Locale;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class TVShowDetailsActivity extends AppCompatActivity {
    private ActivityTVShowDetailsBinding activityTVShowDetailsBinding;
    private TVShowDetailsViewModel tvShowDetailsViewModel;
    private BottomSheetDialog episodesBottomSheetDialog;
    private LayoutEpisodesBottomSheetBinding layoutEpisodesBottomSheetBinding;
    private TVShow tvShow;
    private boolean isTVShowAvailableInWatchlist = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityTVShowDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_t_v_show_details);
        doInitialization();

    }

    private void doInitialization() {
        tvShowDetailsViewModel = new ViewModelProvider(this).get(TVShowDetailsViewModel.class);
        activityTVShowDetailsBinding.imageBack.setOnClickListener(view -> onBackPressed());
        tvShow = (TVShow) getIntent().getSerializableExtra("tvShow");
        getTvShowDetails();
        checkTVShowInWatchlist();
    }


    private void checkTVShowInWatchlist() {
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(tvShowDetailsViewModel.getTVShowFromWatchlist(String.valueOf(tvShow.getId()))
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(tvShow -> {
                    isTVShowAvailableInWatchlist = true;
                    activityTVShowDetailsBinding.imageWatchlistDetail.setImageResource(R.drawable.ic_add);
                    compositeDisposable.dispose();
                }));
    }

    private void getTvShowDetails() {
        activityTVShowDetailsBinding.setIsLoading(true);
        String tvShowId = String.valueOf(tvShow.getId());
        tvShowDetailsViewModel.getTvShowDetails(tvShowId).observe(this, tvShowDetailsResponse -> {
            activityTVShowDetailsBinding.setIsLoading(false);
            if (tvShowDetailsResponse.getTvShowDetails().getPictures() != null) {
                loadImageSlider(tvShowDetailsResponse.getTvShowDetails().getPictures());
            }
            activityTVShowDetailsBinding.setTvShowImageURL(
                    tvShowDetailsResponse.getTvShowDetails().getImage_path()
            );
            activityTVShowDetailsBinding.imageTVShow.setVisibility(View.VISIBLE);
            activityTVShowDetailsBinding.setDescription(
                    String.valueOf(
                            HtmlCompat.fromHtml(
                                    tvShowDetailsResponse.getTvShowDetails().getDescription(),
                                    HtmlCompat.FROM_HTML_MODE_LEGACY
                            ))
            );
            activityTVShowDetailsBinding.textDescription.setVisibility(View.VISIBLE);
            activityTVShowDetailsBinding.textReadMore.setVisibility(View.VISIBLE);
            activityTVShowDetailsBinding.textReadMore.setOnClickListener(view -> {
                if (activityTVShowDetailsBinding.textReadMore.getText().toString().equals("Read More")) {
                    activityTVShowDetailsBinding.textDescription.setMaxLines(Integer.MAX_VALUE);
                    activityTVShowDetailsBinding.textDescription.setEllipsize(null);
                    activityTVShowDetailsBinding.textReadMore.setText(R.string.read_leass);
                } else {
                    activityTVShowDetailsBinding.textDescription.setMaxLines(4);
                    activityTVShowDetailsBinding.textDescription.setEllipsize(TextUtils.TruncateAt.END);
                    activityTVShowDetailsBinding.textReadMore.setText(R.string.read_more);
                }
            });
            activityTVShowDetailsBinding.setRating(
                    String.format(
                            Locale.getDefault(),
                            "%.2f",
                            Double.parseDouble(tvShowDetailsResponse.getTvShowDetails().getRating())
                    )
            );
            if (tvShowDetailsResponse.getTvShowDetails().getGenres() != null) {
                activityTVShowDetailsBinding.setGenre(tvShowDetailsResponse.getTvShowDetails().getGenres()[0]);
            } else {
                activityTVShowDetailsBinding.setGenre("N/A");
            }
            activityTVShowDetailsBinding.setRuntime(tvShowDetailsResponse.getTvShowDetails().getRuntime() + "Min");
            activityTVShowDetailsBinding.viewDivider1.setVisibility(View.VISIBLE);
            activityTVShowDetailsBinding.layoutMisc.setVisibility(View.VISIBLE);
            activityTVShowDetailsBinding.viewDivider2.setVisibility(View.VISIBLE);
            activityTVShowDetailsBinding.buttonWebsite.setOnClickListener(view -> {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(tvShowDetailsResponse.getTvShowDetails().getUrl()));
                startActivity(intent);
            });


            activityTVShowDetailsBinding.buttonWebsite.setVisibility(View.VISIBLE);
            activityTVShowDetailsBinding.buttonEpisodes.setVisibility(View.VISIBLE);
            activityTVShowDetailsBinding.buttonEpisodes.setOnClickListener(view -> {
                if (episodesBottomSheetDialog == null) {
                    episodesBottomSheetDialog = new BottomSheetDialog(TVShowDetailsActivity.this);
                    layoutEpisodesBottomSheetBinding = DataBindingUtil.inflate(
                            LayoutInflater.from(TVShowDetailsActivity.this),
                            R.layout.layout_episodes_bottom_sheet,
                            findViewById(R.id.episodesContainer),
                            false
                    );
                    episodesBottomSheetDialog.setContentView(layoutEpisodesBottomSheetBinding.getRoot());
                    layoutEpisodesBottomSheetBinding.episodesRecyclerView.setAdapter(
                            new EpisodesAdapter(tvShowDetailsResponse.getTvShowDetails().getEpisodes())
                    );
                    layoutEpisodesBottomSheetBinding.textTitle.setText(
                            String.format("Episodes | %s", tvShow.getName())
                    );
                    layoutEpisodesBottomSheetBinding.imageClose.setOnClickListener(view1 -> episodesBottomSheetDialog.dismiss());
                }

                // ---- Optional section Start ----//
                FrameLayout frameLayout = episodesBottomSheetDialog.findViewById(
                        com.google.android.material.R.id.design_bottom_sheet);
                if (frameLayout != null) {
                    BottomSheetBehavior<View> bottomSheetBehavior = BottomSheetBehavior.from(frameLayout);
                    bottomSheetBehavior.setPeekHeight(Resources.getSystem().getDisplayMetrics().heightPixels);
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

                }
                // ---- Optional section end ---- //

                episodesBottomSheetDialog.show();

            });
            activityTVShowDetailsBinding.imageWatchlistDetail.setOnClickListener(view -> {
                CompositeDisposable compositeDisposable = new CompositeDisposable();
                if (isTVShowAvailableInWatchlist) {
                    compositeDisposable.add(tvShowDetailsViewModel.removeTVShowFromWatchlist(tvShow)
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(()->{
                        isTVShowAvailableInWatchlist = false;
                        TempDataHolder.IS_WATCHLIST_UPDATE = true;
                        activityTVShowDetailsBinding.imageWatchlistDetail.setImageResource(R.drawable.ic_red_eye);
                        Toast.makeText(getApplicationContext(), "Remove from watchlist", Toast.LENGTH_SHORT).show();
                        compositeDisposable.dispose();
                    }));

                } else {


                    new CompositeDisposable().add(tvShowDetailsViewModel.addWatchlist(tvShow)
                            .subscribeOn(Schedulers.io()).subscribeOn(AndroidSchedulers.mainThread())
                            .subscribe(() -> {
                                TempDataHolder.IS_WATCHLIST_UPDATE = true;
                                activityTVShowDetailsBinding.imageWatchlistDetail.setImageResource(R.drawable.ic_add);
                                compositeDisposable.dispose();
                            }));
                    Toast.makeText(getApplicationContext(), "Added to watchlist", Toast.LENGTH_LONG).show();
                }
            });
            activityTVShowDetailsBinding.imageWatchlistDetail.setVisibility(View.VISIBLE);
            loadBasicTVShowDetails();
        });
    }

    private void loadImageSlider(String[] sliderImage) {
        activityTVShowDetailsBinding.sliderViewPager.setOffscreenPageLimit(1);
        activityTVShowDetailsBinding.sliderViewPager.setAdapter(new ImageSliderAdapter(sliderImage));
        activityTVShowDetailsBinding.sliderViewPager.setVisibility(View.VISIBLE);
        activityTVShowDetailsBinding.viewFadingEdge.setVisibility(View.VISIBLE);
        setupSliderIndicator(sliderImage.length);
        activityTVShowDetailsBinding.sliderViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentSliderIndicator(position);
            }
        });

    }

    private void setupSliderIndicator(int count) {
        ImageView[] indicator = new ImageView[count];
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(8, 0, 8, 0);
        for (int i = 0; i < indicator.length; i++) {
            indicator[i] = new ImageView(getApplicationContext());
            indicator[i].setImageDrawable(ContextCompat.getDrawable(
                    getApplicationContext(),
                    R.drawable.background_slider_indicator_inactive
            ));
            indicator[i].setLayoutParams(layoutParams);
            activityTVShowDetailsBinding.layoutSliderIndicators.addView(indicator[i]);
        }
        activityTVShowDetailsBinding.layoutSliderIndicators.setVisibility(View.VISIBLE);
        setCurrentSliderIndicator(0);
    }

    private void setCurrentSliderIndicator(int position) {
        int childCount = activityTVShowDetailsBinding.layoutSliderIndicators.getChildCount();
        for (int i = 0; i < childCount; i++) {
            ImageView imageView = (ImageView) activityTVShowDetailsBinding.layoutSliderIndicators.getChildAt(i);
            if (i == position) {
                imageView.setImageDrawable(
                        ContextCompat.getDrawable(getApplicationContext(), R.drawable.background_slider_indicator_active)
                );
            } else {
                imageView.setImageDrawable(
                        ContextCompat.getDrawable(getApplicationContext(), R.drawable.background_slider_indicator_inactive)
                );
            }
        }
    }

    private void loadBasicTVShowDetails() {
        activityTVShowDetailsBinding.setTvShowName(tvShow.getName());
        activityTVShowDetailsBinding.setNetworkCountry(
                tvShow.getNetwork() + " (" + tvShow.getCountry() + " )");
        activityTVShowDetailsBinding.setStatus(tvShow.getStatus());
        activityTVShowDetailsBinding.setStartDate(tvShow.getStart_date());
        activityTVShowDetailsBinding.textName.setVisibility(View.VISIBLE);
        activityTVShowDetailsBinding.textNetworkCountry.setVisibility(View.VISIBLE);
        activityTVShowDetailsBinding.textStatus.setVisibility(View.VISIBLE);
        activityTVShowDetailsBinding.textStartedOn.setVisibility(View.VISIBLE);

    }
}