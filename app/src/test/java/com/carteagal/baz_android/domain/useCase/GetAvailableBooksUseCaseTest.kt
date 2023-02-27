package com.carteagal.baz_android.domain.useCase

import com.carteagal.baz_android.data.local.entities.AvailableBookEntity
import com.carteagal.baz_android.data.local.entities.availableEntitysToUI
import com.carteagal.baz_android.data.local.repository.CryptoLocalRepository
import com.carteagal.baz_android.data.remote.model.AvailableBookResponse
import com.carteagal.baz_android.data.remote.model.base.BaseError
import com.carteagal.baz_android.data.remote.network.Resources
import com.carteagal.baz_android.data.remote.repository.AvailableBooksRepositoryNetwork
import com.carteagal.baz_android.domain.mapper.availableMapper
import com.carteagal.baz_android.domain.useCase.networkUseCase.GetAvailableBooksUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock

internal class GetAvailableBooksUseCaseTest{

    @RelaxedMockK
    private lateinit var availableBooksRepositoryNetwork: AvailableBooksRepositoryNetwork

    @RelaxedMockK
    private lateinit var localRepository: CryptoLocalRepository

    private lateinit var getAvailableBooksUseCase: GetAvailableBooksUseCase

    private val availableBooksResponseMock = listOf(mock<AvailableBookResponse>())

    private val availableBooksEntityMock = listOf(mock<AvailableBookEntity>())

    @Before
    fun onBefore(){
        MockKAnnotations.init(this)
        getAvailableBooksUseCase = GetAvailableBooksUseCase(availableBooksRepositoryNetwork)
    }

    @Test
    fun `When Response Api is Success`() = runBlocking {
        //Given
        coEvery { localRepository.insertAllBooks(availableMapper(availableBooksResponseMock)) }
        coEvery { availableBooksRepositoryNetwork.getAllBooks() } returns flow {
            emit(Resources.Success(data = availableBooksResponseMock))
        }

        //When
        getAvailableBooksUseCase()

        //Then
        Assert.assertEquals(localRepository.getAllBooks(),availableMapper(availableBooksResponseMock))
    }

    @Test
    fun `when The Api Doesnt Return Anything Get Values From DataBase`() = runBlocking {

        //Given
        coEvery { localRepository.insertAllBooks(availableBooksEntityMock.availableEntitysToUI()) }
        coEvery { localRepository.getAllBooks() } returns availableBooksEntityMock.availableEntitysToUI()
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