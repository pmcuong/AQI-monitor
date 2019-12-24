package com.example.aqimonitor.base

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.aqimonitor.base.BaseAdapter.BaseViewHolder
import com.example.aqimonitor.view.viewmodel.MainViewModel
import android.graphics.drawable.GradientDrawable
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.os.Build
import android.view.View


abstract class BaseAdapter<T, VB : ViewDataBinding>(
    var context: Context,
    var data: List<T>
) : RecyclerView.Adapter<BaseViewHolder<T, VB>>() {

    var binding: VB? = null
    var onItemClick: ((Int, String) -> Unit)? = null
    abstract fun getId(): Int

    open fun setListData(data: List<T>?) {
        this.data = data!!
        notifyDataSetChanged()
    }

    abstract fun getLayoutId(): Int

    class BaseViewHolder<T, VB : ViewDataBinding>(private val binding: VB) :
        RecyclerView.ViewHolder(binding.root) {
        fun setVariable(id: Int, t: T) {
            binding.setVariable(id, t)
            binding.executePendingBindings()
            MainViewModel.STATIC_VALUE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<T, VB> {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            getLayoutId(),
            parent,
            false
        )
        return BaseViewHolder(binding!!)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<T, VB>, position: Int) {
        holder.apply {
            setVariable(getId(), data[position])
            itemView.setOnClickListener {
                onItemClick!!(position, "FUCK")
                notifyItemChanged(position)
            }

        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}