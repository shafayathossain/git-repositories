package com.example.github.repositories.features.user

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.net.toUri
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.github.repositories.R
import com.example.github.repositories.features.RepositoryAdapter
import com.example.github.repositories.base.BaseFragment
import com.example.github.repositories.data.model.OwnerDTO
import com.example.github.repositories.data.model.RepositoryDTO
import com.example.github.repositories.features.detail.DetailFragment
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
    private var loader: ProgressBar? = null
    private var user: OwnerDTO? = null

    var viewModelProviderFactor: ViewModelProvider.Factory = UserViewModel.Factory()

    override fun getLayoutId(): Int {
        return R.layout.fragment_user
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel =
            ViewModelProvider(this, viewModelProviderFactor)[UserViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setViewObjects(view)
        setObserver()
        viewModel.fetchUser(user?.login)
    }

    override fun onItemClick(item: RepositoryDTO) {
        val bundle = Bundle()
        bundle.putParcelable(DetailFragment.DETAIL_DATA_TAG, item)
        val fragment = DetailFragment()
        fragment.arguments = bundle

        replaceFragment(fragment)
    }

    private fun setViewObjects(view: View) {
        user = arguments?.getParcelable(USER_DATA_TAG)
        title = view.findViewById(R.id.title)
        image = view.findViewById(R.id.image)
        detail = view.findViewById(R.id.detail)
        url = view.findViewById(R.id.url)
        list = view.findViewById(R.id.list)
        loader = view.findViewById(R.id.loader)

        title?.text = user?.login
        Picasso.get().load(user?.avatarUrl?.toUri()).into(image)
    }

    private fun setObserver() {
        viewModel.message.observe(viewLifecycleOwner) {
            showMessageWithActionButton(it) {
                viewModel.fetchUser(user?.login)
            }
        }
        viewModel.showLoader.observe(viewLifecycleOwner) {
            if (it == true) {
                loader?.visibility = View.VISIBLE
            } else {
                loader?.visibility = View.GONE
            }
        }
        viewModel.user.observeForever {
            if (it.twitterUsername.isNullOrEmpty()) {
                detail?.visibility = View.INVISIBLE
            } else {
                detail?.text = getString(R.string.twitter_handle_text, it.twitterUsername)
            }
            viewModel.fetchRepositories(it.reposUrl!!)
        }
        viewModel.repositories.observeForever {
            list?.adapter = RepositoryAdapter(it.toMutableList(), this)
        }
    }
}