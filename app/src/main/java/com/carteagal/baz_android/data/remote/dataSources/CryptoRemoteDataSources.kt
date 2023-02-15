package com.carteagal.baz_android.data.remote.dataSources

import com.carteagal.baz_android.data.remote.model.AvailableBookResponse
import com.carteagal.baz_android.data.remote.model.OrderBookResponse
import com.carteagal.baz_android.data.remote.model.TickerResponse
import com.carteagal.baz_android.data.remote.model.base.BaseServiceResponse
import io.reactivex.rxjava3.core.Observable

interface CryptoRemoteDataSources {
    suspend fun getAvailableBooks(): BaseServiceResponse<List<AvailableBookResponse>>
    suspend fun getOrderBooks(book: String): BaseServiceResponse<OrderBookResponse>
    suspend fun getTicker(book: String): BaseServiceResponse<TickerResponse>

    fun getTickerRx(book: String): Observable<BaseServiceResponse<TickerResponse>>
}