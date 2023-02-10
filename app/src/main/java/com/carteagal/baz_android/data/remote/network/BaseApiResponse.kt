package com.carteagal.baz_android.data.remote.network

import android.content.Context
import com.carteagal.baz_android.R
import com.carteagal.baz_android.data.remote.model.base.BaseError
import com.carteagal.baz_android.data.remote.model.base.BaseServiceResponse
import com.carteagal.baz_android.data.remote.network.Resources.Error
import com.carteagal.baz_android.data.remote.network.Resources.Success

open class BaseApiResponse {
    suspend fun <T> safeApiCall(
        context: Context,
        apiCall: suspend () -> BaseServiceResponse<T>
    ): Resources<T> {
        try {
            val response = apiCall()
            if(response.success && response.result != null)
                return Success(response.result)
            else
                return Error(error = BaseError(message = response.error!!.message, code = response.error.code))
        }catch (e: Exception){
            return Error(error = BaseError(message = context.getString(R.string.generic_subtitle_error)))
        }
    }
}