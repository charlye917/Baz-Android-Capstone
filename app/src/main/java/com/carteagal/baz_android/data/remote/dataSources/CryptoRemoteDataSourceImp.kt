package com.carteagal.baz_android.data.remote.dataSources

import com.carteagal.baz_android.data.model.AvailableBookResponse
import com.carteagal.baz_android.data.model.OrderBookResponse
import com.carteagal.baz_android.data.model.TickerResponse
import com.carteagal.baz_android.data.model.base.BaseServiceResponse
import com.carteagal.baz_android.data.remote.service.CryptoApiClient
import javax.inject.Inject

class CryptoRemoteDataSourceImp @Inject constructor(
    private val apiClient: CryptoApiClient
) : CryptoRemoteDataSources {

    override suspend fun getAvailableBooks(): BaseServiceResponse<List<AvailableBookResponse>> =
        apiClient.getAvailableBooks()

    override suspend fun getOrderBooks(book: String): BaseServiceResponse<OrderBookResponse> =
        apiClient.getOrderBooks(book)

     override suspend fun getTicker(book: String): BaseServiceResponse<TickerResponse> =
        apiClient.getTicker(book)
}