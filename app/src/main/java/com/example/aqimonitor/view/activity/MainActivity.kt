package com.example.aqimonitor.view.activity

import androidx.lifecycle.Observer
import com.example.aqimonitor.R
import com.example.aqimonitor.base.BaseActivity
import com.example.aqimonitor.databinding.ActivityMainBinding
import com.example.aqimonitor.view.adapter.MainAdapter
import com.example.aqimonitor.view.viewmodel.MainViewModel

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    var adapter: MainAdapter = MainAdapter(this)

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun getClassViewModel(): Class<MainViewModel> {
        return MainViewModel::class.java
    }

    override fun initView() {
        binding?.main = this
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
            viewModel?.searchCity("hanoi")
        }
    }
}

