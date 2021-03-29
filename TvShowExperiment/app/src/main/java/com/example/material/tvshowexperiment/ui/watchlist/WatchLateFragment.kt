package com.example.material.tvshowexperiment.ui.watchlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.material.tvshowexperiment.R
import com.example.material.tvshowexperiment.databinding.WatchLateFragmentBinding
import com.example.material.tvshowexperiment.db.TvShowDatabase

class WatchLateFragment : Fragment() {

    companion object {
        fun newInstance() = WatchLateFragment()
    }

    private lateinit var viewModel: WatchLateViewModel
    private lateinit var binding: WatchLateFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.watch_late_fragment,
            container,
            false
        )
        val application = requireNotNull(this.activity).application
        val dataSource = TvShowDatabase.getInstance(application).tvShowDao()

        val viewModelFactory = WatchLateViewModelFactory(dataSource, application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(WatchLateViewModel::class.java)

        val watchLateAdapter = WatchLateAdapter()
        viewModel.movie.observe(viewLifecycleOwner,{
            it?.let {
                watchLateAdapter.submitList(it)
            }
        })

        binding.watchlistRecyclerView.adapter = watchLateAdapter
        binding.lifecycleOwner = this
        return binding.root
    }

//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProvider(this).get(WatchLateViewModel::class.java)
//    }

}