package com.carteagal.baz_android.domain.useCase.networkUseCase

import com.carteagal.baz_android.data.local.repository.CryptoLocalRepository
import com.carteagal.baz_android.data.remote.model.base.BaseError
import com.carteagal.baz_android.domain.model.AvailableBookUI
import com.carteagal.baz_android.data.remote.network.Resources
import com.carteagal.baz_android.data.remote.network.Resources.Error
import com.carteagal.baz_android.data.remote.network.Resources.Loading
import com.carteagal.baz_android.data.remote.network.Resources.Success
import com.carteagal.baz_android.data.remote.repository.AvailableBooksRepositoryNetwork
import com.carteagal.baz_android.domain.mapper.availableMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetAvailableBooksUseCase @Inject constructor(
    private val availableBooksRepositoryNetwork: AvailableBooksRepositoryNetwork
) {
    suspend operator fun invoke(): Flow<Resources<List<AvailableBookUI>>> = flow {
        availableBooksRepositoryNetwork.getAllBooks()
            .catch {e -> e.printStackTrace() }
            .collect{ state ->
                when(state){
                    is Loading -> {
                        emit(Loading)
                    }
                    is Success -> {
                        emit(Success(data = availableMapper(state.data)))
                    }
                    else -> {
                        val error = state as Error
                        emit(Error(BaseError(message = error.error.message, code = error.error.code)))
                    }
                }
            }
    }.flowOn(Dispatchers.IO)
}