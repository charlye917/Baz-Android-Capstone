package com.carteagal.baz_android.domain.useCase

import com.carteagal.baz_android.R
import com.carteagal.baz_android.data.local.entities.AvailableBookEntity
import com.carteagal.baz_android.data.local.entities.availableEntitysToUI
import com.carteagal.baz_android.data.local.repository.CryptoLocalRepository
import com.carteagal.baz_android.data.remote.model.AvailableBookResponse
import com.carteagal.baz_android.data.remote.model.base.BaseError
import com.carteagal.baz_android.data.remote.network.Resources
import com.carteagal.baz_android.data.remote.repository.AvailableBooksRepositoryNetwork
import com.carteagal.baz_android.domain.mapper.availableMapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

internal class GetAvailableBooksUseCaseTest{

    @RelaxedMockK
    private lateinit var availableBooksRepositoryNetwork: AvailableBooksRepositoryNetwork

    @RelaxedMockK
    private lateinit var localRepository: CryptoLocalRepository

    private lateinit var getAvailableBooksUseCase: GetAvailableBooksUseCase

    val availableBooksResponse = listOf(
        AvailableBookResponse(
            book = "aave_usd",
            minimumPrice = "20000",
            maximumPrice = "40000",
            minimumAmount = "2000",
            maximumAmount = "4000",
            minimumValue = "4",
            maximumValue = "10000",
            tickSize = "5"
        ),
        AvailableBookResponse(
            book = "aave_usd",
            minimumPrice = "20000",
            maximumPrice = "40000",
            minimumAmount = "2000",
            maximumAmount = "4000",
            minimumValue = "4",
            maximumValue = "10000",
            tickSize = "5"
        )
    )

    val availableBooksEntity = listOf(
        AvailableBookEntity(
            fullName = "aave_usd",
            name = "Aave",
            typeMoney =  "USD",
            imageUrl = "https://raw.githubusercontent.com/spothq/cryptocurrency-icons/master/128/color/aave.png",
            maxPrice = 1000.0,
            minPrice = 3.0,
            amount = 200000.0
        ),
        AvailableBookEntity(
            fullName = "algo_usd",
            name = "Algorand",
            typeMoney =  "USD",
            imageUrl = "https://raw.githubusercontent.com/spothq/cryptocurrency-icons/master/128/color/algo.png",
            maxPrice = 3.0,
            minPrice = 0.008,
            amount = 6.0E7
        )
    )

    @Before
    fun onBefore(){
        MockKAnnotations.init(this)
        getAvailableBooksUseCase = GetAvailableBooksUseCase(
            availableBooksRepositoryNetwork,
            localRepository
        )
    }

    @Test
    fun `When Response Api is Success`() = runBlocking {
        //Given
        coEvery { availableBooksRepositoryNetwork.getAllBooks() } returns flow {
            emit(Resources.Success(data = availableBooksResponse))
        }
        coEvery { localRepository.insertAllBooks(availableMapper(availableBooksResponse)) }
        
        //When
        getAvailableBooksUseCase()

        //Then
        coVerify { localRepository.insertAllBooks(availableMapper(availableBooksResponse)) }
        coVerify { flow {emit(Resources.Success(data = availableMapper(availableBooksResponse))) }}
    }

    @Test
    fun `when The Api Doesnt Return Anything Get Values From DataBase`() = runBlocking {

        //Given
        coEvery { localRepository.insertAllBooks(availableBooksEntity.availableEntitysToUI()) }
        coEvery { localRepository.getAllBooks() } returns availableBooksEntity.availableEntitysToUI()
        coEvery { availableBooksRepositoryNetwork.getAllBooks() } returns flow {
                    emit(Resources.Error(error = BaseError(message = "Por el momento, no se pudo completar la solicitud. Intenta más tarde.")))
        }
        //When
        getAvailableBooksUseCase()
        val localData = localRepository.getAllBooks()

        //Then
        coVerify { flow {  emit((Resources.Success(data = localData)))} }
    }
}