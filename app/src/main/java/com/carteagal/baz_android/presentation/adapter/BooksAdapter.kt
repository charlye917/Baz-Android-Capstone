package com.carteagal.baz_android.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.carteagal.baz_android.domain.model.AvailableBookUI
import com.carteagal.baz_android.databinding.ItemBookListBinding
import com.carteagal.baz_android.utils.extension.View.loadImage
import com.carteagal.baz_android.utils.extension.getAbbreviation
import com.carteagal.baz_android.utils.extension.toAmountFormat

class BooksAdapter(
    private val onClickListener: (bookInfo: AvailableBookUI) -> Unit
): ListAdapter<AvailableBookUI, BooksAdapter.ViewHolder>(AvailableBookCallBack){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemBookListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(getItem(position))

    inner class ViewHolder(private val binding: ItemBookListBinding) :
        RecyclerView.ViewHolder(binding.root){
        @SuppressLint("SetTextI18n")
        fun bind(book: AvailableBookUI){
            binding.txtNameBook.text = book.name
            binding.txtMaxAmount.text = "${book.maxPrice?.toAmountFormat()} ${book.typeMoney}"
            binding.txtMinAmount.text = "${book.minPrice?.toAmountFormat()} ${book.typeMoney}"
            binding.txtAbv.text = "${book.fullName?.getAbbreviation()} - ${book.typeMoney}"
            binding.imgLogo.loadImage(book.imageUrl ?: "")
            binding.cardViewInfo.setOnClickListener { onClickListener(book) }
        }
    }

    private object AvailableBookCallBack: DiffUtil.ItemCallback<AvailableBookUI>(){
        override fun areItemsTheSame(
            oldItem: AvailableBookUI,
            newItem: AvailableBookUI
        ) = oldItem.name == newItem.name

        override fun areContentsTheSame(
            oldItem: AvailableBookUI,
            newItem: AvailableBookUI
        ) = oldItem == newItem
    }
}