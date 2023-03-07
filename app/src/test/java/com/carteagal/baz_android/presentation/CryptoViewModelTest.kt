package com.carteagal.baz_android.presentation

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.carteagal.baz_android.data.remote.model.base.BaseError
import com.carteagal.baz_android.data.remote.network.CheckInternetConnection
import com.carteagal.baz_android.data.remote.network.Resources
import com.carteagal.baz_android.domain.model.AskBindUI
import com.carteagal.baz_android.domain.model.AvailableBookUI
import com.carteagal.baz_android.domain.model.TickerUI
import com.carteagal.baz_android.domain.useCase.localUseCase.GetAskBindLocalUseCase
import com.carteagal.baz_android.domain.useCase.localUseCase.GetAvailableBookLocalUseCase
import com.carteagal.baz_android.domain.useCase.localUseCase.GetTickerLocalUseCase
import com.carteagal.baz_android.domain.useCase.networkUseCase.GetAskBindUseCase
import com.carteagal.baz_android.domain.useCase.networkUseCase.GetAvailableBooksUseCase
import com.carteagal.baz_android.domain.useCase.networkUseCase.GetTickerRxUseCase
import com.carteagal.baz_android.domain.useCase.networkUseCase.GetTickerUserCase
import com.carteagal.baz_android.presentation.viewmodel.CryptoViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock

@ExperimentalCoroutinesApi
class CryptoViewModelTest {

    @RelaxedMockK
    private lateinit var getAvailableBooksUseCase: GetAvailableBooksUseCase

    @RelaxedMockK
    private lateinit var getAvailableBoookLocalUseCase: GetAvailableBookLocalUseCase

    @RelaxedMockK
    private lateinit var getAskBindUseCase: GetAskBindUseCase

    @RelaxedMockK
    private lateinit var getAskBindLocalUseCase: GetAskBindLocalUseCase

    @RelaxedMockK
    private lateinit var getTickerUserCase: GetTickerUserCase

    @RelaxedMockK
    private lateinit var getTickerLocalUseCase: GetTickerLocalUseCase

    @RelaxedMockK
    private lateinit var getTickerRxUseCase: GetTickerRxUseCase

    @RelaxedMockK
    private lateinit var context: Context

    private lateinit var cryptoViewModel: CryptoViewModel

    @get:Rule
    val rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    private val bookName = "aave_usd"

    private var listAvailableBookUIMock = listOf(mock<AvailableBookUI>())

    private var tickerUIMock = mock<TickerUI>()

    private var listAskBindUIMock = listOf(mock<AskBindUI>())

    @Before
    fun onBefore(){
        MockKAnnotations.init(this)
        cryptoViewModel = CryptoViewModel(
            getAvailableBooksUseCase,
            getAvailableBoookLocalUseCase,
            getAskBindUseCase,
            getAskBindLocalUseCase,
            getTickerUserCase,
            getTickerLocalUseCase,
            getTickerRxUseCase,
            context
        )
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun onAfter(){
        Dispatchers.resetMain()
    }

    @Test
    fun `when getAvailableBookNetworkUseCase return a success response`() = runTest {
        //Given
        coEvery { CheckInternetConnection.hasInternetConnection(context) } returns true
        coEvery { getAvailableBooksUseCase() } returns flow {
            emit(Resources.Success(data = listAvailableBookUIMock))
        }

        //When
        cryptoViewModel.getAvailableBooks()

        //Then
        assert(cryptoViewModel.availableBooks.value == listAvailableBookUIMock)
    }

    @Test
    fun `when getBookDetail askbind return a success response`() = runTest {
        //Given
        coEvery { CheckInternetConnection.hasInternetConnection(context) } returns true
        coEvery { getAskBindUseCase(bookName) } returns flow {
            emit(Resources.Success(data = listAskBindUIMock))
        }

        //When
        cryptoViewModel.getBookDetailData(bookName)

        //Then
        assert(cryptoViewModel.askBindList.value == listAskBindUIMock)
    }

    @Test
    fun `when getBookDetail ticker return a success response`() = runTest {
        //Given
        coEvery { CheckInternetConnection.hasInternetConnection(context) } returns true
        coEvery { getTickerUserCase(bookName) } returns flow {
            emit(Resources.Success(data = tickerUIMock))
        }

        //When
        cryptoViewModel.getBookDetailData(bookName)

        //Then
        assert(cryptoViewModel.ticker.value == tickerUIMock)
    }

    @Test
    fun `when getAvailableBookNetworkUseCase return a fail response but db have information`() = runTest {
        //Given
        coEvery { CheckInternetConnection.hasInternetConnection(context) } returns true
        coEvery { getAvailableBooksUseCase() } returns flow {
            emit(Resources.Error(error = BaseError(message = "Por el momento, no se pudo completar la solicitud. Intenta más tarde.")))
        }
        coEvery { getAvailableBoookLocalUseCase.getAllLocalBooks() } returns listAvailableBookUIMock

        //When
        cryptoViewModel.getAvailableBooks()

        //Then
        assert(cryptoViewModel.availableBooks.value == listAvailableBookUIMock)
    }

    @Test
    fun `when getAskBindNetworkUseCase return a fail response but db have information`() = runTest {
        //Given
        coEvery { CheckInternetConnection.hasInternetConnection(context) } returns false
        coEvery { getAskBindUseCase(bookName) } returns flow {
            emit(Resources.Error(error = BaseError(message = "Por el momento, no se pudo completar la solicitud. Intenta más tarde.")))
        }
        coEvery { getAskBindLocalUseCase.allAskBindBooks(bookName) } returns listAskBindUIMock

        //When
        cryptoViewModel.getBookDetailData(bookName)

        //Then
        assert(cryptoViewModel.askBindList.value == listAskBindUIMock)
    }


    @Test
    fun `If getAvailableBookUseCase retunrn an succes response but dont have internet`() = runTest {
        //Given
        coEvery { CheckInternetConnection.hasInternetConnection(context) } returns false
        coEvery { getAvailableBooksUseCase() } returns flow {
            emit(Resources.Success(data = listAvailableBookUIMock))
        }

        //When
        cryptoViewModel.getAvailableBooks()

        //Then
        assertEquals(cryptoViewModel.isError.value, true)
    }



    @Test
    fun `If getTicker retunrn an error response`() = runTest {
        //Given
        coEvery { CheckInternetConnection.hasInternetConnection(context) } returns true
        coEvery { getTickerUserCase(bookName) } returns flow {
            emit(Resources.Error(error = BaseError(message = "Por el momento, no se pudo completar la solicitud. Intenta más tarde.")))
        }

        //When
        cryptoViewModel.getBookDetailData(bookName)

        //Then
        assert(cryptoViewModel.isError.value == true)
    }
}