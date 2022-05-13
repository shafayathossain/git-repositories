package com.example.github.repositories.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.github.repositories.DetailFragment
import com.example.github.repositories.R
import com.example.github.repositories.RepositoryAdapter
import com.example.github.repositories.data.RepositoryDTO

class MainFragment : Fragment(), RepositoryAdapter.RepositoryAdapterCallback {

    private lateinit var viewModel: MainViewModel

    private var swipeRefresh: SwipeRefreshLayout? = null
    private var recyclerview: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, MainViewModel.Factory()).get(MainViewModel::class.java)
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        viewModel.fetchItems()

        swipeRefresh = view.findViewById(R.id.swipe_refresh)
        swipeRefresh!!.setOnRefreshListener { viewModel.refresh() }

        recyclerview = view.findViewById(R.id.news_list)
        recyclerview!!.layoutManager = LinearLayoutManager(context)

        viewModel.repositories.observeForever {
            val adapter = RepositoryAdapter(it.take(20).toMutableList(), this)
            recyclerview!!.adapter = adapter
        }
        return view
    }

    override fun onItemClick(item: RepositoryDTO) {
        activity?.supportFragmentManager
            ?.beginTransaction()
            ?.replace(android.R.id.content, DetailFragment(item))
            ?.addToBackStack("detail")
            ?.commit()
    }
}