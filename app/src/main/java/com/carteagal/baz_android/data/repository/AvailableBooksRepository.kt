package com.carteagal.baz_android.data.repository

import android.content.Context
import android.util.Log
import com.carteagal.baz_android.R
import com.carteagal.baz_android.data.model.AvailableBookResponse
import com.carteagal.baz_android.data.model.base.BaseError
import com.carteagal.baz_android.data.local.dao.AvailableBookDao
import com.carteagal.baz_android.data.local.entities.availableEntitysToResponse
import com.carteagal.baz_android.data.local.entities.availableResponcesToEntity
import com.carteagal.baz_android.data.remote.dataSources.CryptoRemoteDataSources
import com.carteagal.baz_android.data.remote.network.Resources
import com.carteagal.baz_android.data.remote.network.Resources.*
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AvailableBooksRepository @Inject constructor(
    private val apiDataSource: CryptoRemoteDataSources,
    private val availableBookDao: AvailableBookDao,
    @ApplicationContext private val context: Context
) {
    suspend fun getAllBooks(): Flow<Resources<List<AvailableBookResponse>>> = flow{
        emit(Loading)
        val localData = availableBookDao.getAllBooks()
        try {
            val response = apiDataSource.getAvailableBooks()
            when{
                response.success && !response.result.isNullOrEmpty() -> {
                    availableBookDao.deleteAllBooks()
                    availableBookDao.insertAllBooks(response.result.availableResponcesToEntity())
                    emit(Success(response.result))
                }
                localData.isNotEmpty() -> { emit(Success(localData.availableEntitysToResponse())) }
                else -> { emit(Error(error = BaseError(message = response.error!!.message, code = response.error.code))) }
            }
        }catch (e: Exception){
            if(localData.isNotEmpty())
                emit(Success(localData.availableEntitysToResponse()))
            else
                emit(Error(error = BaseError(message = context.getString(R.string.generic_subtitle_error))))
        }
    }
}