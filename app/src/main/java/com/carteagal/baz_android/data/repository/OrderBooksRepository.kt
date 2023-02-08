package com.carteagal.baz_android.data.repository

import android.content.Context
import android.util.Log
import com.carteagal.baz_android.R
import com.carteagal.baz_android.data.local.dao.OrderBookDao
import com.carteagal.baz_android.data.remote.dataSources.CryptoRemoteDataSources
import com.carteagal.baz_android.data.model.OrderBookResponse
import com.carteagal.baz_android.data.model.base.BaseError
import com.carteagal.baz_android.data.remote.network.Resources
import com.carteagal.baz_android.data.remote.network.Resources.Error
import com.carteagal.baz_android.data.remote.network.Resources.Loading
import com.carteagal.baz_android.data.remote.network.Resources.Success
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class OrderBooksRepository @Inject constructor(
    private val apiDataSources: CryptoRemoteDataSources,
    //private val orderBookDao: OrderBookDao,
    @ApplicationContext private val context: Context
) {
   suspend fun getOrderBook(book: String): Flow<Resources<OrderBookResponse>> = flow {
       emit(Loading)
       //val localData = orderBookDao.getBook(book)
       try {
           val response = apiDataSources.getOrderBooks(book)
           when{
               response.success && response.result != null -> {
                  // orderBookDao.deleteBook(book)
                   //orderBookDao.insetOrderBook(response.result.orderBookResponseToEntity(book))
                   emit(Success(response.result))
               }
               //localData.bookName != null -> { emit(Success(localData.orderBookEntityToResponse())) }
               else -> { emit(Error(error = BaseError(message = context.getString(R.string.generic_subtitle_error)))) }
           }
       }catch (e: Exception){
           //if(localData.bookName != null)
            //   emit(Success(localData.orderBookEntityToResponse()))
           //else
           Log.d("__tag ex", e.message.toString())
               emit(Error(error = BaseError(message = context.getString(R.string.generic_subtitle_error))))
       }
   }
}