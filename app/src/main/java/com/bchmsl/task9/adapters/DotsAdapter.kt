package com.bchmsl.task9.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bchmsl.task9.databinding.LayoutDotCheckedBinding
import com.bchmsl.task9.databinding.LayoutDotUncheckedBinding
import com.bchmsl.task9.model.dots.DotModel

class DotsAdapter: ListAdapter<DotModel, RecyclerView.ViewHolder>(DotsCallback()) {
    companion object{
        private const val IS_NOT_CHECKED = 0
        private const val IS_CHECKED = 1
    }

    inner class CheckedViewHolder(binding:LayoutDotCheckedBinding):RecyclerView.ViewHolder(binding.root)
    inner class UnCheckedViewHolder(binding:LayoutDotUncheckedBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            IS_CHECKED -> CheckedViewHolder(LayoutDotCheckedBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            else -> UnCheckedViewHolder(LayoutDotUncheckedBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {}

    override fun getItemViewType(position: Int): Int {
        return when(getItem(position).isChecked){
            true -> IS_CHECKED
            false -> IS_NOT_CHECKED
        }
    }

    class DotsCallback: DiffUtil.ItemCallback<DotModel>(){
        override fun areItemsTheSame(oldItem: DotModel, newItem: DotModel): Boolean {
            return oldItem.position == newItem.position
        }

        override fun areContentsTheSame(oldItem: DotModel, newItem: DotModel): Boolean {
            return oldItem == newItem
        }

    }
}