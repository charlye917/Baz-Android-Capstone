package com.carteagal.baz_android.data.remote.repository

import android.content.Context
import com.carteagal.baz_android.data.remote.dataSources.CryptoRemoteDataSources
import com.carteagal.baz_android.data.remote.model.OrderBookResponse
import com.carteagal.baz_android.data.remote.network.BaseApiResponse
import com.carteagal.baz_android.data.remote.network.Resources
import com.carteagal.baz_android.data.remote.network.Resources.Loading
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class OrderBooksRepositoryNetwork @Inject constructor(
    private val apiDataSources: CryptoRemoteDataSources,
    @ApplicationContext private val context: Context
) : BaseApiResponse() {
   suspend fun getOrderBook(book: String): Flow<Resources<OrderBookResponse>> = flow {
       emit(Loading)
       emit(safeApiCall(context){ apiDataSources.getOrderBooks(book) })
   }
}