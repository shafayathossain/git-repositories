package com.example.github.repositories.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {

    private var communicator: BaseFragmentCommunicator? = null

    abstract fun getLayoutId(): Int

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BaseFragmentCommunicator) {
            communicator = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(getLayoutId(), container, false)
    }

    fun replaceFragment(fragment: Fragment) {
        communicator?.replaceFragment(fragment)
    }
}
