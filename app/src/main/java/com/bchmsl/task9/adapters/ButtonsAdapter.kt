package com.bchmsl.task9.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bchmsl.task9.databinding.LayoutRvButtonNonumericBinding
import com.bchmsl.task9.databinding.LayoutRvButtonNumericBinding
import com.bchmsl.task9.model.buttons.ButtonModel

class ButtonsAdapter : ListAdapter<ButtonModel, ViewHolder>(ButtonsCallback()) {
    companion object {
        private const val BUTTON_TYPE_NUMERIC = 0
        private const val BUTTON_TYPE_ICON = 1
    }

    var itemClick: ((ButtonModel)-> Unit)? = null
    inner class NumericViewHolder(private val binding: LayoutRvButtonNumericBinding) :
        ViewHolder(binding.root) {
        fun bind() {
            val currentItem = getItem(adapterPosition)
            binding.apply {
                tvValue.text = currentItem.value.toString()
                root.setOnClickListener {
                    itemClick?.invoke(currentItem)
                }
            }
        }
    }

    inner class NoNumericViewHolder(private val binding: LayoutRvButtonNonumericBinding) :
        ViewHolder(binding.root) {
        fun bind() {
            val currentItem = getItem(adapterPosition)
            binding.apply {
                ivValue.setImageResource(currentItem.value)
                root.setOnClickListener {
                    itemClick?.invoke(currentItem)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            BUTTON_TYPE_NUMERIC -> {
                NumericViewHolder(
                    LayoutRvButtonNumericBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            else -> {
                NoNumericViewHolder(
                    LayoutRvButtonNonumericBinding.inflate(
                        LayoutInflater.from(
                            parent.context
                        ), parent, false
                    )
                )
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder) {
            is NumericViewHolder -> holder.bind()
            is NoNumericViewHolder -> holder.bind()
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position).isNumeric) {
            true -> BUTTON_TYPE_NUMERIC
            false -> BUTTON_TYPE_ICON
        }
    }

    class ButtonsCallback : DiffUtil.ItemCallback<ButtonModel>() {
        override fun areItemsTheSame(oldItem: ButtonModel, newItem: ButtonModel): Boolean {
            return oldItem.value == newItem.value
        }

        override fun areContentsTheSame(oldItem: ButtonModel, newItem: ButtonModel): Boolean {
            return oldItem == newItem
        }

    }


}