package com.carteagal.baz_android.presentation.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.carteagal.baz_android.data.remote.model.AskBindsResponse
import com.carteagal.baz_android.data.remote.model.OrderBookResponse
import com.carteagal.baz_android.databinding.ItemDetailsBookBinding
import com.carteagal.baz_android.databinding.ItemPresentationBookBinding
import com.carteagal.baz_android.domain.model.AskBindUI
import com.carteagal.baz_android.utils.TypeAskBid
import com.carteagal.baz_android.utils.TypeAskBid.ASKS
import com.carteagal.baz_android.utils.TypeAskBid.BIDS
import com.carteagal.baz_android.utils.extension.toAmountFormat

class AskBindsAdapter(): ListAdapter<AskBindUI, AskBindsAdapter.ViewHolder>(AskBindCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemDetailsBookBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )


    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(getItem(position))

    inner class ViewHolder(private val binding: ItemDetailsBookBinding):
        RecyclerView.ViewHolder(binding.root){

        fun bind(askBinds: AskBindUI) {
            binding.txtAmount.text = askBinds.amount.toString()
            binding.txtPrice.text = askBinds.price.toAmountFormat()
        }
    }

    private object AskBindCallback: DiffUtil.ItemCallback<AskBindUI>(){
        override fun areItemsTheSame(
            oldItem: AskBindUI,
            newItem: AskBindUI
        ): Boolean {
            return oldItem.book == newItem.book
        }

        override fun areContentsTheSame(
            oldItem: AskBindUI,
            newItem: AskBindUI
        ): Boolean {
            return oldItem == newItem
        }

    }
}