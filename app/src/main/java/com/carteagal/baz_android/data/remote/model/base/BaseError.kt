package com.carteagal.baz_android.data.remote.model.base

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class BaseError(
    @Expose
    @SerializedName("code") val code: String = "-1",

    @Expose
    @SerializedName("mesage") val message: String
)