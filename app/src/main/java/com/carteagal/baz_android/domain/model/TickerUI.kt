package com.carteagal.baz_android.domain.model

data class TickerUI(
    val fullName: String? = "",
    val bookName: String? = "",
    val typeMoney: String? = "",
    val price: Double? = 0.0,
    val highPrice: Double? = 0.0,
    val lowPrice: Double? = 0.0,
    val ask: Double? = 0.0,
    val bind: Double? = 0.0,
    val lastModification: String? = "",
    val urlBook: String? = ""
)