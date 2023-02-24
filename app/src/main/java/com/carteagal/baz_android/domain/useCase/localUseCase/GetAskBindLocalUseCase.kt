package com.carteagal.baz_android.domain.useCase.localUseCase

import com.carteagal.baz_android.data.local.repository.CryptoLocalRepository
import com.carteagal.baz_android.domain.model.AskBindUI
import javax.inject.Inject

class GetAskBindLocalUseCase @Inject constructor(
    private val localRepository: CryptoLocalRepository
) {
    suspend fun insertAskBindBooks(list: List<AskBindUI>, book: String){
        localRepository.insertAskBind(list, book)
    }

    suspend fun allAskBindBooks(book: String): List<AskBindUI> =
        localRepository.getAllAskBind(book)

}