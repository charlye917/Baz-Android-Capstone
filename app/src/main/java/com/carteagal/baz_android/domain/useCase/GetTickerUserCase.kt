package com.carteagal.baz_android.domain.useCase

import android.content.Context
import android.util.Log
import com.carteagal.baz_android.R
import com.carteagal.baz_android.data.model.TickerResponse
import com.carteagal.baz_android.data.model.base.BaseError
import com.carteagal.baz_android.data.remote.network.Resources
import com.carteagal.baz_android.data.remote.network.Resources.Error
import com.carteagal.baz_android.data.remote.network.Resources.Success
import com.carteagal.baz_android.data.repository.TickerRepository
import com.carteagal.baz_android.domain.mapper.tickerMapper
import com.carteagal.baz_android.domain.model.TickerUI
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class GetTickerUserCase @Inject constructor(
    private val tickerRepository: TickerRepository,
    @ApplicationContext private val context: Context
) {
    suspend operator fun invoke(book: String): Flow<Resources<TickerUI>> = flow {
        tickerRepository.getTickerInfo(book)
            .catch { e -> e.printStackTrace() }
            .collect{ state ->
                Log.d("__tag usercase", state.toString())
                when(state){
                    is Success -> {
                        val newTicker = tickerMapper(state.data)
                        emit(Success(newTicker))
                    }
                    is Error -> {
                        val error = state.error
                        emit(Error(BaseError(message = error.message, code = error.code)))
                    }
                    else -> {
                        emit(Error(BaseError(message = context.getString(R.string.generic_subtitle_error))))
                    }
                }
            }
    }
}