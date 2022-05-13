package com.example.github.repositories

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.github.repositories.base.BaseFragment
import com.example.github.repositories.data.OwnerDTO
import com.example.github.repositories.data.RepositoryDTO
import com.example.github.repositories.detail.DetailFragment
import com.squareup.picasso.Picasso

class UserFragment : BaseFragment(),
    RepositoryAdapter.RepositoryAdapterCallback {

    companion object {
        const val USER_DATA_TAG = "user"
    }

    private lateinit var viewModel: UserViewModel

    private var title: TextView? = null
    private var image: ImageView? = null
    private var detail: TextView? = null
    private var url: TextView? = null
    private var list: RecyclerView? = null
    private var user: OwnerDTO? = null

    override fun getLayoutId(): Int {
        return R.layout.fragment_user
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, UserViewModel.Factory()).get(UserViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        user = arguments?.getParcelable(USER_DATA_TAG)
        title = view.findViewById(R.id.title)
        image = view.findViewById(R.id.image)
        detail = view.findViewById(R.id.detail)
        url = view.findViewById(R.id.url)
        list = view.findViewById(R.id.list)

        title?.text = user?.login
        Picasso.get().load(user?.avatar_url?.toUri()).into(image)

        viewModel.fetchUser(user?.login)
        viewModel.user.observeForever {
            detail?.text = "Twitter handle: " + it.twitter_username
            viewModel.fetchRepositories(it.repos_url!!)
        }
        viewModel.repositories.observeForever {
            list?.adapter = RepositoryAdapter(it.toMutableList(), this)
        }

    }

    override fun onItemClick(item: RepositoryDTO) {
        val bundle = Bundle()
        bundle.putParcelable(DetailFragment.DETAIL_DATA_TAG, item)
        val fragment = DetailFragment()
        fragment.arguments = bundle

        replaceFragment(fragment)
    }
}