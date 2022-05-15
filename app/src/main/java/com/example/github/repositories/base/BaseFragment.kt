package com.example.github.repositories.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

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

    fun showMessage(message: String?) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun showMessageWithActionButton(it: String?, action: () -> Unit) {
        val snackbar = Snackbar
            .make(requireView(), it ?: "", 7000)
            .setAction("RETRY") {
                action.invoke()
            }
        snackbar.show()
    }
}
