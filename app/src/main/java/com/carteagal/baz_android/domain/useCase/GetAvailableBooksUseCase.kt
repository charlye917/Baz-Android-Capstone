package com.carteagal.baz_android.domain.useCase

import android.content.Context
import android.util.Log
import com.carteagal.baz_android.R
import com.carteagal.baz_android.data.model.base.BaseError
import com.carteagal.baz_android.domain.model.AvailableBookUI
import com.carteagal.baz_android.data.remote.network.Resources
import com.carteagal.baz_android.data.remote.network.Resources.Error
import com.carteagal.baz_android.data.remote.network.Resources.Success
import com.carteagal.baz_android.data.repository.AvailableBooksRepository
import com.carteagal.baz_android.domain.mapper.availableMapper
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetAvailableBooksUseCase @Inject constructor(
    private val availableBooksRepository: AvailableBooksRepository,
    @ApplicationContext private val context: Context
) {
    suspend operator fun invoke(): Flow<Resources<List<AvailableBookUI>>> = flow {
        availableBooksRepository.getAllBooks()
            .catch {e -> e.printStackTrace() }
            .collect{ state ->
                when(state){
                    is Success -> {
                        val newDataUI = availableMapper(state.data)
                        emit(Success(data = newDataUI))
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
    }.flowOn(Dispatchers.IO)
}