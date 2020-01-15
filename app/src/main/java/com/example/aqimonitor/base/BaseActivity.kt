package com.example.aqimonitor.base

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.example.aqimonitor.extention.setStatusBarGradient

abstract class BaseActivity<VB : ViewDataBinding, VM : ViewModel> : AppCompatActivity() {
    abstract fun getLayoutId(): Int
    protected var binding: VB? = null
    protected var viewModel: VM? = null

    abstract fun getClassViewModel(): Class<VM>
    abstract fun initView()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, getLayoutId())
        viewModel = ViewModelProviders.of(this).get(getClassViewModel())
        setStatusBarGradient()
        initView()
    }

    public abstract fun onClick(v: View)
}