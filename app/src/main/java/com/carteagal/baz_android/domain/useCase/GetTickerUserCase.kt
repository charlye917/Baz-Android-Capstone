package com.carteagal.baz_android.domain.useCase

import com.carteagal.baz_android.data.local.repository.CryptoLocalRepository
import com.carteagal.baz_android.data.remote.model.base.BaseError
import com.carteagal.baz_android.data.remote.network.Resources
import com.carteagal.baz_android.data.remote.network.Resources.Error
import com.carteagal.baz_android.data.remote.network.Resources.Loading
import com.carteagal.baz_android.data.remote.network.Resources.Success
import com.carteagal.baz_android.data.remote.repository.TickerRepositoryNetwork
import com.carteagal.baz_android.domain.mapper.tickerMapper
import com.carteagal.baz_android.domain.model.TickerUI
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetTickerUserCase @Inject constructor(
    private val tickerRepositoryNetwork: TickerRepositoryNetwork,
    private val localRepository: CryptoLocalRepository
    ) {
    suspend operator fun invoke(book: String): Flow<Resources<TickerUI>> = flow {
        val localData = localRepository.getTickerUI(book)
        tickerRepositoryNetwork.getTickerInfo(book)
            .catch { e -> e.printStackTrace() }
            .collect{ state ->
                when(state){
                    is Loading -> { emit(Loading) }
                    is Success -> {
                        val newTicker = tickerMapper(state.data)
                        localRepository.insertTicker(newTicker)
                        emit(Success(newTicker))
                    }
                    else -> {
                        val error = state as Error
                        if(localData.fullName != null)
                            emit(Success(data = localRepository.getTickerUI(book)))
                        else
                            emit(Error(BaseError(message = error.error.message, code = error.error.code)))
                    }
                }
            }
    }
}