package com.carteagal.baz_android.domain.useCase.localUseCase

import com.carteagal.baz_android.data.local.repository.CryptoLocalRepository
import com.carteagal.baz_android.domain.model.TickerUI
import javax.inject.Inject

class GetTickerLocalUseCase @Inject constructor(
    private val localRepository: CryptoLocalRepository
) {
    suspend fun insertTickerLocal(ticker: TickerUI){
        localRepository.insertTicker(ticker)
    }

    suspend fun getTickerLocal(book: String): TickerUI =
        localRepository.getTickerUI(book)
}