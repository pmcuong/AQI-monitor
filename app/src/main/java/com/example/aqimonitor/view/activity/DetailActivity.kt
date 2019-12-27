package com.example.aqimonitor.view.activity

import android.content.Intent
import android.util.Log
import android.view.View
import com.example.aqimonitor.BR
import com.example.aqimonitor.R
import com.example.aqimonitor.base.BaseActivity
import com.example.aqimonitor.databinding.ActivityDetailBinding
import com.example.aqimonitor.extention.*
import com.example.aqimonitor.model.AQIModel
import com.example.aqimonitor.view.viewmodel.DetailViewModel

class DetailActivity : BaseActivity<ActivityDetailBinding, DetailViewModel>() {
    val TAG = javaClass.name
    var dataAqi:AQIModel? = null
    override fun getLayoutId(): Int {
        return R.layout.activity_detail
    }

    override fun getClassViewModel(): Class<DetailViewModel> {
        return DetailViewModel::class.java
    }

    override fun initView() {
        setStatusBarGradient()
        getExtra(intent)
        initToolbar()
        updateUI()
    }

    override fun onClick(v: View) {
        when(v.id) {
            R.id.iv_back->{
                finish()
            }
        }
    }

    private fun getExtra(mIntent : Intent?) {
        dataAqi = mIntent?.getParcelableExtra("aqi_data")
        Log.d(TAG, ": $dataAqi");
    }

    private fun initToolbar() {
        binding?.toolbar?.ivBack?.visibility = View.VISIBLE
        binding?.toolbar?.addressContainer?.visibility = View.VISIBLE
        binding?.toolbar?.tvTitle?.visibility = View.GONE
        binding?.toolbar?.tvNameAddress?.text = dataAqi?.nameAddress
        binding?.toolbar?.tvAddress?.text = dataAqi?.address
    }

    private fun updateUI() {
        binding?.toolbar?.setVariable(BR.baseActivity, this)
        binding?.aqiModel = dataAqi
        binding?.tvTime?.setCurrentTime()
        binding?.backgroundAqiIndex?.setGradientColor(getColorFromAqiIndex(dataAqi?.aqiIndex!!))
        binding?.ivAqiLevel?.setFaceFromAqiIndex(dataAqi?.aqiIndex!!)
        binding?.tvNameAqiLevel?.setText(getNameOfAqiLevel(dataAqi?.aqiIndex!!))
    }
}