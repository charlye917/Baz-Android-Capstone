package com.carteagal.baz_android.data.remote.dataSources

import com.carteagal.baz_android.data.remote.model.AvailableBookResponse
import com.carteagal.baz_android.data.remote.model.OrderBookResponse
import com.carteagal.baz_android.data.remote.model.TickerResponse
import com.carteagal.baz_android.data.remote.model.base.BaseServiceResponse
import com.carteagal.baz_android.data.remote.service.CryptoApiClient
import io.reactivex.rxjava3.core.Observable
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

    override fun getTickerRx(book: String): Observable<BaseServiceResponse<TickerResponse>> =
        apiClient.getTickerRx(book)

}