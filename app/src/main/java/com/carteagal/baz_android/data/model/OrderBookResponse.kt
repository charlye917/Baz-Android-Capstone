package com.carteagal.baz_android.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class OrderBookResponse(
    @Expose
    @SerializedName("updated_at") val updatedAt: String?,
    @Expose
    @SerializedName("sequence") val sequence: String?,
    @Expose
    @SerializedName("bids") val bids: List<AskBindsResponse>?,
    @Expose
    @SerializedName("asks") val asks: List<AskBindsResponse>?
)

data class AskBindsResponse(
    @Expose
    @SerializedName("book") val book: String?,
    @Expose
    @SerializedName("price") val price: String?,
    @Expose
    @SerializedName("amount") val amount: String?
)