package com.example.material.mosttvshow.ui.mostpopular

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.material.mosttvshow.R
import com.example.material.mosttvshow.databinding.MostPopularFragmentBinding
import com.example.material.mosttvshow.domain.TvShow

class MostPopularFragment : Fragment() {

    private val viewModel: MostPopularViewModel by lazy {
        ViewModelProvider(this)
            .get(MostPopularViewModel::class.java)
    }
    private lateinit var binding: MostPopularFragmentBinding
    private var adapter = MostPopularAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.tvShows.observe(viewLifecycleOwner, Observer<List<TvShow>> {listTvShows ->
            listTvShows?.apply {
                adapter.submitList(listTvShows)
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.most_popular_fragment,
                container,
                false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.tvShowRecyclerView.adapter = adapter
        viewModel.eventNetworkError.observe(viewLifecycleOwner, { isNetworkError ->
            if(isNetworkError)
            onNetworkError()
        })
        return binding.root
    }

    private fun onNetworkError() {
        if (!viewModel.isNetworkErrorShown.value!!) {
            Toast.makeText(activity, "Network Error", Toast.LENGTH_SHORT).show()
            viewModel.onNetworkErrorShown()
        }
    }
}