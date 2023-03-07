package com.carteagal.baz_android.domain.useCase.networkUseCase

import com.carteagal.baz_android.data.local.repository.CryptoLocalRepository
import com.carteagal.baz_android.data.remote.model.base.BaseError
import com.carteagal.baz_android.data.remote.network.Resources
import com.carteagal.baz_android.data.remote.network.Resources.Error
import com.carteagal.baz_android.data.remote.network.Resources.Loading
import com.carteagal.baz_android.data.remote.network.Resources.Success
import com.carteagal.baz_android.data.remote.repository.OrderBooksRepositoryNetwork
import com.carteagal.baz_android.domain.mapper.askBindMapper
import com.carteagal.baz_android.domain.model.AskBindUI
import com.carteagal.baz_android.utils.TypeAskBid.ASKS
import com.carteagal.baz_android.utils.TypeAskBid.BIDS
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetAskBindUseCase @Inject constructor(
    private val orderBooksRepositoryNetwork: OrderBooksRepositoryNetwork
) {

    suspend operator fun invoke(book: String): Flow<Resources<List<AskBindUI>>> = flow {
        orderBooksRepositoryNetwork.getOrderBook(book)
            .catch { e -> e.printStackTrace() }
            .collect{ state ->
                when(state){
                    is Loading -> { emit(Loading) }
                    is Success -> {
                        val data = state.data
                        val newDataUI = mutableListOf<AskBindUI>()
                        newDataUI += askBindMapper(data.asks, ASKS)
                        newDataUI += askBindMapper(data.bids, BIDS)
                        emit(Success(data = newDataUI))
                    }
                    else -> {
                        val error = state as Error
                        emit(Error(BaseError(message = error.error.message, code = error.error.code)))
                    }
                }
            }
    }.flowOn(Dispatchers.IO)
}