package com.example.aqimonitor.view.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import com.example.aqimonitor.R
import com.example.aqimonitor.base.BaseActivity
import com.example.aqimonitor.databinding.ActivityMainBinding
import com.example.aqimonitor.extention.setStatusBarGradient
import com.example.aqimonitor.model.AQIModel
import com.example.aqimonitor.view.adapter.MainAdapter
import com.example.aqimonitor.view.viewmodel.MainViewModel

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {
    override fun onClick(v: View) {
       when (v?.id) {
           R.id.tv_add -> {Toast.makeText(this, "Add item", Toast.LENGTH_SHORT).show()}
           R.id.tv_map -> {Toast.makeText(this, "Go to map", Toast.LENGTH_SHORT).show()}
       }
    }

    var adapter: MainAdapter = MainAdapter(this)

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun getClassViewModel(): Class<MainViewModel> {
        return MainViewModel::class.java
    }

    override fun initView() {
        binding?.main = this
        setStatusBarGradient()
        binding?.recyclerView?.adapter = adapter


        viewModel?.setListData()
        viewModel?.listData?.observe(this, Observer { listData ->
            adapter.setListData(listData)
        })

//        adapter.onItemClick = object : OnItemClickedListener {
//            override fun onLongClick(position: Int) {
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//            }
//
//            override fun onClick(position: Int) {
//                Toast.makeText(applicationContext, adapter.data[position].content, Toast.LENGTH_SHORT).show()
////                viewModel?.setListData()
//            }
//        }

        adapter.onItemClick = { position, content ->
            Log.d("MainActivity", ": $content");
            openDetaiActivity(content)
            viewModel?.searchCity("hanoi")
        }
    }

    fun openDetaiActivity(aqiData: AQIModel) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("aqi_data", aqiData)
        startActivity(intent)
    }
}

