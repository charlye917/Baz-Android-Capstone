package com.carteagal.baz_android.data.remote.service

import com.carteagal.baz_android.data.remote.model.AvailableBookResponse
import com.carteagal.baz_android.data.remote.model.base.BaseServiceResponse
import com.carteagal.baz_android.data.remote.model.OrderBookResponse
import com.carteagal.baz_android.data.remote.model.TickerResponse
import com.carteagal.baz_android.utils.Constants.PATH_AVAILABLE_BOOKS
import com.carteagal.baz_android.utils.Constants.PATH_ORDER_BOOK
import com.carteagal.baz_android.utils.Constants.PATH_TICKER
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface CryptoApiClient {

    @GET(PATH_AVAILABLE_BOOKS)
    suspend fun getAvailableBooks(): BaseServiceResponse<List<AvailableBookResponse>>

    @GET(PATH_ORDER_BOOK)
    suspend fun getOrderBooks(
        @Query("book") book: String
    ): BaseServiceResponse<OrderBookResponse>

    @GET(PATH_TICKER)
    suspend fun getTicker(
        @Query("book") book: String
    ): BaseServiceResponse<TickerResponse>

    @GET(PATH_TICKER)
    fun getTickerRx(
        @Query("book") book: String
    ): Observable<BaseServiceResponse<TickerResponse>>
}