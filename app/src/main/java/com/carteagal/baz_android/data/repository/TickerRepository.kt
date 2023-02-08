package com.carteagal.baz_android.data.repository

import android.content.Context
import android.util.Log
import com.carteagal.baz_android.R
import com.carteagal.baz_android.data.remote.dataSources.CryptoRemoteDataSources
import com.carteagal.baz_android.data.model.TickerResponse
import com.carteagal.baz_android.data.model.base.BaseError
import com.carteagal.baz_android.data.local.dao.TickerDao
import com.carteagal.baz_android.data.local.entities.TickerEntity
import com.carteagal.baz_android.data.local.entities.tickerEntityToResponse
import com.carteagal.baz_android.data.local.entities.tickerResponseToEntity
import com.carteagal.baz_android.data.remote.network.Resources
import com.carteagal.baz_android.data.remote.network.Resources.Error
import com.carteagal.baz_android.data.remote.network.Resources.Loading
import com.carteagal.baz_android.data.remote.network.Resources.Success
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TickerRepository @Inject constructor(
    private val apiDataSource: CryptoRemoteDataSources,
    private val tickerDao: TickerDao,
    @ApplicationContext private val context: Context
){
    suspend fun getTickerInfo(book: String): Flow<Resources<TickerResponse>> = flow {
        emit(Loading)
        val localData = tickerDao.getTicker(book)
        try {
            val response = apiDataSource.getTicker(book)
            when{
                response.success && response.result != null-> {
                    tickerDao.deleteTicker(book)
                    tickerDao.insertTicker(response.result.tickerResponseToEntity())
                    emit(Success(response.result))
                }
                localData.bookName != null -> { emit(Success(localData.tickerEntityToResponse())) }
                else -> { emit(Error(error = BaseError(message = response.error!!.message, code = response.error.code)))}
            }
        }catch (e: Exception){
            if(localData.bookName != null)
                emit(Success(localData.tickerEntityToResponse()))
            else
                emit(Error(error = BaseError(message = context.getString(R.string.generic_subtitle_error))))
        }
    }
}