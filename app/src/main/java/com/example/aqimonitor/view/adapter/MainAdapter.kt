package com.example.aqimonitor.view.adapter

import android.content.Context
import com.example.aqimonitor.BR
import com.example.aqimonitor.R
import com.example.aqimonitor.base.BaseAdapter
import com.example.aqimonitor.databinding.ItemMainBinding
import com.example.aqimonitor.model.MainItem

class MainAdapter(context: Context, data: List<MainItem>? = ArrayList()) :
    BaseAdapter<MainItem, ItemMainBinding>(context, data!!) {

    constructor(context: Context, text: String) : this(context)

    constructor(context: Context, number: Int) : this(context)

    override fun getId(): Int {
        return BR.mainItem
    }

    override fun getLayoutId(): Int {
        return R.layout.item_main
    }

    override fun setListData(data: List<MainItem>?) {
        this.data = data!!
        notifyDataSetChanged()
    }

}