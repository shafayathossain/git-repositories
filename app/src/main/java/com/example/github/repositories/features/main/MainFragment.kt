package com.example.github.repositories.features.main

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.github.repositories.R
import com.example.github.repositories.base.BaseFragment
import com.example.github.repositories.data.model.RepositoryDTO
import com.example.github.repositories.features.RepositoryAdapter
import com.example.github.repositories.features.detail.DetailFragment


class MainFragment : BaseFragment(), RepositoryAdapter.RepositoryAdapterCallback {

    private lateinit var viewModel: MainViewModel
    private var swipeRefresh: SwipeRefreshLayout? = null
    private var recyclerview: RecyclerView? = null
    private var loader: ProgressBar? = null

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    var viewModelProvider: ViewModelProvider.Factory = MainViewModel.Factory()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelProvider)[MainViewModel::class.java]
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_main
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setViewObjects(view)
        setObservers()
        viewModel.fetchItems()
    }

    private fun setViewObjects(view: View) {
        swipeRefresh = view.findViewById(R.id.swipe_refresh)
        swipeRefresh?.setOnRefreshListener { viewModel.refresh() }
        swipeRefresh?.isRefreshing = false

        recyclerview = view.findViewById(R.id.news_list)
        recyclerview?.layoutManager = LinearLayoutManager(context)

        loader = view.findViewById(R.id.loader)
    }

    private fun setObservers() {
        viewModel.message.observe(viewLifecycleOwner) {
            showMessageWithActionButton(it) {
                viewModel.fetchItems()
            }
        }
        viewModel.showLoader.observe(viewLifecycleOwner) {
            if (it == true) {
                loader?.visibility = View.VISIBLE
            } else {
                loader?.visibility = View.GONE
            }
        }
        viewModel.repositories.observe(viewLifecycleOwner) {
            loadItemsIntoRecyclerView(it)
        }
    }

    private fun loadItemsIntoRecyclerView(it: List<RepositoryDTO>) {
        swipeRefresh?.isRefreshing = false
        val adapter = RepositoryAdapter(it, this)
        recyclerview?.adapter = adapter
    }

    override fun onItemClick(item: RepositoryDTO) {
        val bundle = Bundle()
        bundle.putParcelable(DetailFragment.DETAIL_DATA_TAG, item)
        val fragment = DetailFragment()
        fragment.arguments = bundle

        replaceFragment(fragment)
    }
}