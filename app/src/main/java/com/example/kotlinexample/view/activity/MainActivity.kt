package com.example.kotlinexample.view.activity

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.kotlinexample.R
import com.example.kotlinexample.base.BaseActivity
import com.example.kotlinexample.base.OnItemClickedListener
import com.example.kotlinexample.databinding.ActivityMainBinding
import com.example.kotlinexample.view.adapter.MainAdapter
import com.example.kotlinexample.view.viewmodel.MainViewModel
import com.example.kotlinexample.view.viewmodel.getData

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
//            override fun onClick(position: Int) {
//                Toast.makeText(applicationContext, adapter.data[position].content, Toast.LENGTH_SHORT).show()
////                viewModel?.setListData()
//            }
//        }

        adapter.onItemClick = { position, content ->
            showToast(this, "")

            Toast.makeText(this, adapter.data[position].content, Toast.LENGTH_SHORT).show()
            Toast.makeText(this, content, Toast.LENGTH_SHORT).show()
            "20/11/2019".toDateShow()
//            R.string.app_name.getString()
        }
    }
}

fun showToast(context: Context, content: String) {
    Toast.makeText(context, content, Toast.LENGTH_SHORT).show()
}

fun String.toDateShow(): String {
    return  "text"
}
