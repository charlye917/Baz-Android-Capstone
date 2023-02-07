package com.carteagal.baz_android.data.remote.network

import com.carteagal.baz_android.data.model.base.BaseError

sealed class Resources <out T>{
    data class Success<out T: Any>(val data: T): Resources<T>()
    object Loading: Resources<Nothing>()
    data class Error(val error: BaseError): Resources<Nothing>()
}