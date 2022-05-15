package com.example.github.repositories.features.detail

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.example.github.repositories.R
import com.example.github.repositories.features.user.UserFragment
import com.example.github.repositories.base.BaseFragment
import com.example.github.repositories.data.LocalDataStore
import com.example.github.repositories.data.RepositoryDTO
import com.squareup.picasso.Picasso

class DetailFragment : BaseFragment() {

    companion object {
        const val DETAIL_DATA_TAG = "detail"
    }

    private var title: TextView? = null
    private var image: ImageView? = null
    private var detail: TextView? = null
    private var description: TextView? = null
    private var url: TextView? = null
    private var repository: RepositoryDTO? = null

    override fun getLayoutId(): Int {
        return R.layout.fragment_detail
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        repository = arguments?.getParcelable(DETAIL_DATA_TAG)

        setViewObjects(view)
        loadDataIntoView()
        image?.setOnClickListener {
            toggleBookmarkState()
        }
        detail!!.setOnClickListener {
            navigateToUserFragment()
        }
    }

    private fun loadDataIntoView() {
        title?.text = repository?.name
        detail?.text = getString(
            R.string.repository_creation_date_text,
            repository?.owner!!.login,
            repository?.created_at
        )
        Picasso.get().load(repository?.owner!!.avatar_url).into(image)
        description?.text = repository?.description
        url?.text = repository?.html_url

        image?.setImageResource(
            if (LocalDataStore.instance.getBookmarks().contains(repository?.id))
                R.drawable.baseline_bookmark_black_24
            else
                R.drawable.baseline_bookmark_border_black_24
        )
    }

    private fun setViewObjects(view: View) {
        title = view.findViewById(R.id.title)
        image = view.findViewById(R.id.iv_bookmark)
        detail = view.findViewById(R.id.detail)
        description = view.findViewById(R.id.description)
        url = view.findViewById(R.id.url)
    }

    private fun navigateToUserFragment() {
        repository?.let {
            val bundle = Bundle()
            bundle.putParcelable(UserFragment.USER_DATA_TAG, it.owner)
            val fragment = UserFragment()
            fragment.arguments = bundle

            replaceFragment(fragment)
        }
    }

    private fun toggleBookmarkState() {
        repository?.let {
            val isBookmarked = LocalDataStore.instance.getBookmarks().contains(it.id)
            LocalDataStore.instance.bookmarkRepo(it, !isBookmarked)
            image!!.setImageResource(if (!isBookmarked) R.drawable.baseline_bookmark_black_24 else R.drawable.baseline_bookmark_border_black_24)
        }
    }
}