package com.carteagal.baz_android.data.remote.dataSources

import com.carteagal.baz_android.data.model.AvailableBookResponse
import com.carteagal.baz_android.data.model.orderBook.OrderBookResponse
import com.carteagal.baz_android.data.model.TickerResponse
import com.carteagal.baz_android.data.model.base.BaseServiceResponse
import com.carteagal.baz_android.data.remote.network.Resources

interface CryptoRemoteDataSources {
    suspend fun getAvailableBooks():  BaseServiceResponse<List<AvailableBookResponse>>
    suspend fun getOrderBooks(book: String): BaseServiceResponse<List<OrderBookResponse>>
    suspend fun getTicker(book: String): BaseServiceResponse<TickerResponse>
}