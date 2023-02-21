package com.carteagal.baz_android.domain.mapper

import com.carteagal.baz_android.data.remote.model.AskBindsResponse
import com.carteagal.baz_android.domain.model.AskBindUI
import com.carteagal.baz_android.utils.TypeAskBid

fun askBindMapper(list: List<AskBindsResponse>?, type: TypeAskBid) =
    list?.map {
        AskBindUI(
            book = it.book.toString(),
            amount = it.amount?.toDoubleOrNull() ?: 0.0,
            price = it.price?.toDoubleOrNull() ?: 0.0,
            type = type
        )
    } ?: listOf()