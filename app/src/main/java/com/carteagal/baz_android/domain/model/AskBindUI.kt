package com.carteagal.baz_android.domain.model

import com.carteagal.baz_android.utils.TypeAskBid

data class AskBindUI(
    val book: String,
    val amount: Double,
    val price: Double,
    val type: TypeAskBid
)