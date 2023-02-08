package com.carteagal.baz_android.data.model

import android.os.Parcelable
import com.carteagal.baz_android.utils.Constants.BASE_IMAGE_URL
import com.carteagal.baz_android.utils.Constants.EXTENSION_PNG
import com.carteagal.baz_android.utils.Constants.SIZE_COLOR
import com.carteagal.baz_android.utils.extension.separeteUnderscore
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class AvailableBookResponse(
    @Expose
    @SerializedName("book") val book: String? = "",
    @Expose
    @SerializedName("minimum_price") val minimumPrice: String?,
    @Expose
    @SerializedName("maximum_price") val maximumPrice: String?,
    @Expose
    @SerializedName("minimum_amount") val minimumAmount: String?,
    @Expose
    @SerializedName("maximum_amount") val maximumAmount: String?,
    @Expose
    @SerializedName("minimum_value") val minimumValue: String?,
    @Expose
    @SerializedName("maximum_value") val maximumValue: String?,
    @Expose
    @SerializedName("tick_size") val tickSize: String?,
): Parcelable{
    fun generateUrlImage() =
        "$BASE_IMAGE_URL$SIZE_COLOR${book?.separeteUnderscore()}$EXTENSION_PNG"
}