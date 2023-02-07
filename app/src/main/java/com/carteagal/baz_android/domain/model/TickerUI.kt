package com.carteagal.baz_android.domain.model

data class TickerUI(
    val bookName: String,
    val price: Double,
    val highPrice: Double,
    val lowPrice: Double,
    val ask: Double,
    val bind: Double,
    val lastModification: String,
    val urlBook: String
)