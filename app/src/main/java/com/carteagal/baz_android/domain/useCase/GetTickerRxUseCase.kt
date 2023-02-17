package com.carteagal.baz_android.domain.useCase

import com.carteagal.baz_android.data.local.repository.CryptoLocalRepository
import com.carteagal.baz_android.data.remote.network.Resources
import com.carteagal.baz_android.data.remote.repository.TickerRepositoryRxNetwork
import com.carteagal.baz_android.domain.mapper.tickerMapper
import com.carteagal.baz_android.domain.model.TickerUI
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class GetTickerRxUseCase @Inject constructor(
    private val tickerRepositoryNetwork: TickerRepositoryRxNetwork,
    private val localRepository: CryptoLocalRepository
) {
    operator fun invoke(book: String): Observable<TickerUI> {
        return tickerRepositoryNetwork.getTickerInfoRx(book).map {
            val newData = tickerMapper(it.result!!)
            if(it.success)
                localRepository.insertTickerRx(newData)
            newData
        }.subscribeOn(Schedulers.io())
    }
}