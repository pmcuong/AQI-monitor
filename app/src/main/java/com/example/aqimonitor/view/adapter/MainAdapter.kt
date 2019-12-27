package com.example.aqimonitor.view.adapter

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.view.View
import com.example.aqimonitor.BR
import com.example.aqimonitor.R
import com.example.aqimonitor.base.BaseAdapter
import com.example.aqimonitor.databinding.ItemAqiMonitorBinding
import com.example.aqimonitor.extention.*
import com.example.aqimonitor.model.AQIModel

class MainAdapter(context: Context, data: List<AQIModel>? = ArrayList()) :
    BaseAdapter<AQIModel, ItemAqiMonitorBinding>(context, data!!) {

    constructor(context: Context, text: String) : this(context)

    constructor(context: Context, number: Int) : this(context)

    override fun getId(): Int {
        return BR.aqiModel
    }

    override fun getLayoutId(): Int {
        return R.layout.item_aqi_monitor
    }

    override fun setListData(data: List<AQIModel>?) {
        this.data = data!!
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(
        holder: BaseViewHolder<AQIModel, ItemAqiMonitorBinding>,
        position: Int
    ) {
        super.onBindViewHolder(holder, position)
        binding?.tvAqiIndex?.setGradientColor(getColorFromAqiIndex(context, data.get(position).aqiIndex!!))
        binding?.tvNameAqiLevel?.setText(getNameOfAqiLevel(context, data.get(position).aqiIndex!!))
        binding?.root?.setBorderBackground(context, getColorFromAqiIndex(context, data.get(position).aqiIndex!!))
        binding?.ivAqiLevel?.setFaceFromAqiIndex(data.get(position).aqiIndex!!)
        binding?.tvAqiIndex?.setGradientColor(context.getColorFromAqiIndex(data.get(position).aqiIndex!!))
        binding?.tvNameAqiLevel?.setText(context.getNameOfAqiLevel(data.get(position).aqiIndex!!))
    }
}
