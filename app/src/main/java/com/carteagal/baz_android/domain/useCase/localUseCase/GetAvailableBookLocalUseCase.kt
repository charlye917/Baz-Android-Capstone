package com.carteagal.baz_android.domain.useCase.localUseCase

import com.carteagal.baz_android.data.local.repository.CryptoLocalRepository
import com.carteagal.baz_android.domain.model.AvailableBookUI
import javax.inject.Inject

class GetAvailableBookLocalUseCase @Inject constructor(
    private val localRepository: CryptoLocalRepository
) {
    suspend fun insertAllBooks(listBooks: List<AvailableBookUI>){
        localRepository.insertAllBooks(listBooks)
    }

    suspend fun getAllLocalBooks(): List<AvailableBookUI> =
        localRepository.getAllBooks()

}