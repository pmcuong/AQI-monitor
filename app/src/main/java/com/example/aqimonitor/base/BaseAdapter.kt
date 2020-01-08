package com.example.aqimonitor.base

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.aqimonitor.base.BaseAdapter.BaseViewHolder


abstract class BaseAdapter<T, VB : ViewDataBinding>(
    var context: Context,
    var data: List<T>
) : RecyclerView.Adapter<BaseViewHolder<T, VB>>() {

    var binding: VB? = null
    var onItemClick: ((Int, T) -> Unit)? = null
    abstract fun getId(): Int

    open fun setListData(data: List<T>?) {
        this.data = data!!
        notifyDataSetChanged()
    }

    abstract fun getLayoutId(): Int

    class BaseViewHolder<T, VB : ViewDataBinding>(val binding: VB) :
        RecyclerView.ViewHolder(binding.root) {
        fun setVariable(id: Int, t: T) {
            binding.setVariable(id, t)
            binding.executePendingBindings()
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
                onItemClick?.let {
                    onItemClick!!(position, data[position])
                }
            }

        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}