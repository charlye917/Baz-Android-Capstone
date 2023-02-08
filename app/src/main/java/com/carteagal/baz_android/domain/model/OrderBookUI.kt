package com.carteagal.baz_android.domain.model

data class OrderBookUI(
    val bids: List<BidsAskUI>,
    val ask: List<BidsAskUI>
)

data class BidsAskUI(
    val amount: String,
    val price: String
)