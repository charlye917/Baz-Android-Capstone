package com.carteagal.baz_android.data.remote.repository

import android.content.Context
import com.carteagal.baz_android.data.remote.dataSources.CryptoRemoteDataSources
import com.carteagal.baz_android.data.remote.model.TickerResponse
import com.carteagal.baz_android.data.remote.network.BaseApiResponse
import com.carteagal.baz_android.data.remote.network.Resources
import com.carteagal.baz_android.data.remote.network.Resources.Loading
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TickerRepositoryNetwork @Inject constructor(
    private val apiDataSource: CryptoRemoteDataSources,
    @ApplicationContext private val context: Context
): BaseApiResponse() {
    suspend fun getTickerInfo(book: String): Flow<Resources<TickerResponse>> = flow {
        emit(Loading)
        emit(safeApiCall(context){ apiDataSource.getTicker(book) })
    }
}