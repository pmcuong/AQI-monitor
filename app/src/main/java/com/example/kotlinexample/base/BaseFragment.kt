package com.example.kotlinexample.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders

abstract class BaseFragment<VB: ViewDataBinding, VM: ViewModel>: Fragment() {

    var activity: BaseActivity<VB, VM>? = null
    var viewModel: VM? = null
    var binding: VB? = null
    var rootView: View? = null

    abstract fun getLayoutId(): Int
    abstract fun getClassViewModel(): Class<VM>
    abstract fun initView(): Void

    var viewGroup: ViewGroup? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BaseActivity<*, *>) {
            activity = context as BaseActivity<VB, VM>
            activity?.onAttachFragment(this)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        viewModel = ViewModelProviders.of(this).get(getClassViewModel())
        rootView = binding?.root
        return rootView
    }

    override fun onDetach() {
        activity = null
        super.onDetach()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewGroup = getView() as ViewGroup?
        initView()
    }
}