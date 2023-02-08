package com.carteagal.baz_android.domain.useCase

import android.content.Context
import com.carteagal.baz_android.R
import com.carteagal.baz_android.data.model.OrderBookResponse
import com.carteagal.baz_android.data.model.base.BaseError
import com.carteagal.baz_android.data.remote.network.Resources
import com.carteagal.baz_android.data.remote.network.Resources.Error
import com.carteagal.baz_android.data.remote.network.Resources.Success
import com.carteagal.baz_android.data.repository.OrderBooksRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetOrderBookUseCase @Inject constructor(
    private val orderBooksRepository: OrderBooksRepository,
    @ApplicationContext private val context: Context
) {

    suspend operator fun invoke(book: String): Flow<Resources<OrderBookResponse>> = flow {
        orderBooksRepository.getOrderBook(book)
            .catch { e -> e.printStackTrace() }
            .collect{ state ->
                when(state){
                    is Success -> {
                        val newDataUI = state.data//orderBooksMapper(state.data)
                        emit(Success(data = newDataUI))
                    }
                    is Error -> {
                        val error = state.error
                        emit(Error(BaseError(message = error.message, code = error.code)))
                    }
                    else -> { emit(Error(BaseError(message = context.getString(R.string.generic_subtitle_error))))}
                }
            }
    }.flowOn(Dispatchers.IO)

}