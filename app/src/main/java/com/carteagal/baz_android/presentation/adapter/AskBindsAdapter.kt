package com.carteagal.baz_android.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.carteagal.baz_android.data.model.AskBindsResponse
import com.carteagal.baz_android.data.model.OrderBookResponse
import com.carteagal.baz_android.databinding.ItemDetailsBookBinding
import com.carteagal.baz_android.databinding.ItemPresentationBookBinding
import com.carteagal.baz_android.utils.extension.toAmountFormat

class AskBindsAdapter(): ListAdapter<AskBindsResponse, AskBindsAdapter.ViewHolder>(AskBindCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemDetailsBookBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )


    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(getItem(position))

    inner class ViewHolder(private val binding: ItemDetailsBookBinding):
        RecyclerView.ViewHolder(binding.root){

        fun bind(askBinds: AskBindsResponse){
            binding.txtAmount.text = askBinds.amount
            binding.txtPrice.text = askBinds.price!!.toDouble().toAmountFormat()
        }
    }

    private object AskBindCallback: DiffUtil.ItemCallback<AskBindsResponse>(){
        override fun areItemsTheSame(
            oldItem: AskBindsResponse,
            newItem: AskBindsResponse
        ): Boolean {
            return oldItem.book == newItem.book
        }

        override fun areContentsTheSame(
            oldItem: AskBindsResponse,
            newItem: AskBindsResponse
        ): Boolean {
            return oldItem == newItem
        }

    }
}