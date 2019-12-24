package com.example.kotlinexample.view.adapter

import android.content.Context
import com.example.kotlinexample.BR
import com.example.kotlinexample.R
import com.example.kotlinexample.base.BaseAdapter
import com.example.kotlinexample.databinding.ItemMainBinding
import com.example.kotlinexample.model.MainItem

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