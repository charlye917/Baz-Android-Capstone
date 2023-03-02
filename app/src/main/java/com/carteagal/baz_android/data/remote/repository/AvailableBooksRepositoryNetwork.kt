package com.carteagal.baz_android.data.remote.repository

import android.content.Context
import com.carteagal.baz_android.data.remote.model.AvailableBookResponse
import com.carteagal.baz_android.data.remote.dataSources.CryptoRemoteDataSources
import com.carteagal.baz_android.data.remote.network.BaseApiResponse
import com.carteagal.baz_android.data.remote.network.Resources
import com.carteagal.baz_android.data.remote.network.Resources.*
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class AvailableBooksRepositoryNetwork @Inject constructor(
    private val apiDataSource: CryptoRemoteDataSources,
    @ApplicationContext private val context: Context
) : BaseApiResponse() {

    suspend fun getAllBooks(): Flow<Resources<List<AvailableBookResponse>>> = flow{
        emit(Loading)
        emit(safeApiCall(context){ apiDataSource.getAvailableBooks() })
    }.flowOn(Dispatchers.IO)
}