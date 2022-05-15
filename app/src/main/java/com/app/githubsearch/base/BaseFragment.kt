package com.app.githubsearch.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding


abstract class BaseFragment<VB : ViewBinding> : Fragment() {

    private var _bi: VB? = null
    protected val bi: VB get() = _bi!!

    abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VB

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _bi = bindingInflater(inflater, container, false)
        return _bi!!.root
    }

    protected fun hideActionBar() {
        (activity as AppCompatActivity?)!!.supportActionBar?.hide()
    }

    protected fun showActionBar() {
        (activity as AppCompatActivity?)!!.supportActionBar?.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _bi = null
    }
}