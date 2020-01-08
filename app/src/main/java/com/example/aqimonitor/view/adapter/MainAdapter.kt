package com.example.aqimonitor.view.adapter

import android.content.Context
import android.util.Log
import android.view.View
import com.example.aqimonitor.BR
import com.example.aqimonitor.R
import com.example.aqimonitor.base.BaseAdapter
import com.example.aqimonitor.databinding.ItemAqiMonitorBinding
import com.example.aqimonitor.extention.*
import com.example.aqimonitor.generated.callback.OnClickListener
import com.example.aqimonitor.model.AQIModel

class MainAdapter(context: Context, data: List<AQIModel>? = ArrayList()) :
    BaseAdapter<AQIModel, ItemAqiMonitorBinding>(context, data!!) {

    constructor(context: Context, text: String) : this(context)

    constructor(context: Context, number: Int) : this(context)

    var onFollowChange: ((Int, Boolean) -> Unit)? = null

    override fun getId(): Int {
        return BR.aqiModel
    }

    override fun getLayoutId(): Int {
        return R.layout.item_aqi_monitor
    }



    override fun onBindViewHolder(
        holder: BaseViewHolder<AQIModel, ItemAqiMonitorBinding>,
        position: Int
    ) {
        super.onBindViewHolder(holder, position)
        Log.d("MainAdapter", ": ${data[position].aqiIndex}, ${data[position].address}, ${context.getNameOfAqiLevel(data.get(position).aqiIndex!!)}");
        binding?.root?.setBorderBackground(context, context.getColorFromAqiIndex(data.get(position).aqiIndex!!))
        binding?.ivAqiLevel?.setFaceFromAqiIndex(data.get(position).aqiIndex!!)
        binding?.backgroundAqiIndex?.setGradientColor(context.getColorFromAqiIndex(data.get(position).aqiIndex!!))
        Log.d("MainAdapter", ": " + binding?.tvNameAqiLevel);
        binding?.tvNameAqiLevel?.setText(context.getNameOfAqiLevel(data.get(position).aqiIndex!!))
        if (data[position].isCurrentPosition) {
            binding?.containerFollowButton?.visibility = View.GONE
        }else {
            binding?.containerFollowButton?.visibility = View.VISIBLE
        }

        val onClickListener = View.OnClickListener {v: View ->
            when(v.id) {
                R.id.tv_follow -> onFollowChange!!(position, true)
                R.id.tv_unfollow -> onFollowChange!!(position, false)
            }
        }
        binding?.tvFollow?.setOnClickListener(onClickListener)
        binding?.tvUnfollow?.setOnClickListener(onClickListener)
    }
}
