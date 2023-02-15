package com.carteagal.baz_android.domain.useCase

import com.carteagal.baz_android.data.remote.repository.TickerRepositoryRxNetwork
import javax.inject.Inject

class GetTickerRxUseCase @Inject constructor(
    private val tickerRepositoryNetwork: TickerRepositoryRxNetwork,
) {

}