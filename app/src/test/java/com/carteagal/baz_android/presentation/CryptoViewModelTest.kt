package com.carteagal.baz_android.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.carteagal.baz_android.data.remote.model.base.BaseError
import com.carteagal.baz_android.data.remote.network.Resources
import com.carteagal.baz_android.domain.model.AskBindUI
import com.carteagal.baz_android.domain.model.AvailableBookUI
import com.carteagal.baz_android.domain.model.TickerUI
import com.carteagal.baz_android.domain.useCase.GetAskBindUseCase
import com.carteagal.baz_android.domain.useCase.GetAvailableBooksUseCase
import com.carteagal.baz_android.domain.useCase.GetTickerRxUseCase
import com.carteagal.baz_android.domain.useCase.GetTickerUserCase
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
    private lateinit var getAskBindUseCase: GetAskBindUseCase

    @RelaxedMockK
    private lateinit var getTickerUserCase: GetTickerUserCase

    @RelaxedMockK
    private lateinit var getTickerRxUseCase: GetTickerRxUseCase

    private lateinit var cryptoViewModel: CryptoViewModel

    @get:Rule
    val rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    val bookName = "aave_usd"

    private var listAvailableBookUIMock = listOf(mock<AvailableBookUI>())

    private var tickerUIMock = mock<TickerUI>()

    private var listAskBindUIMock = listOf(mock<AskBindUI>())

    @Before
    fun onBefore(){
        MockKAnnotations.init(this)
        cryptoViewModel = CryptoViewModel(
            getAvailableBooksUseCase,
            getAskBindUseCase,
            getTickerUserCase,
            getTickerRxUseCase
        )
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun onAfter(){
        Dispatchers.resetMain()
    }

    @Test
    fun `when getAvailableBookUseCase return a success response`() = runTest {
        //Given
        coEvery { getAvailableBooksUseCase() } returns flow {
            emit(Resources.Success(data = listAvailableBookUIMock))
        }

        //When
        cryptoViewModel.getAvailableBooks()

        //Then
        assert(cryptoViewModel.availableBooks.value == listAvailableBookUIMock)
    }

    @Test
    fun `If getAvailableBookUseCase retunrn an error response`() = runTest {
        //Given
        coEvery { getAvailableBooksUseCase() } returns flow {
            emit(Resources.Error(error = BaseError(message = "Por el momento, no se pudo completar la solicitud. Intenta más tarde.")))
        }

        //When
        cryptoViewModel.getAvailableBooks()

        //Then
        assertEquals(cryptoViewModel.isError.value, true)
    }

    @Test
    fun `when getTicker return a success response`() = runTest {
        //Given
        coEvery { getTickerUserCase(bookName) } returns flow {
            emit(Resources.Success(data = tickerUIMock))
        }

        //When
        cryptoViewModel.getTicker(bookName)

        //Then
        assert(cryptoViewModel.ticker.value == tickerUIMock)
    }

    @Test
    fun `If getTicker retunrn an error response`() = runTest {
        //Given
        coEvery { getTickerUserCase(bookName) } returns flow {
            emit(Resources.Error(error = BaseError(message = "Por el momento, no se pudo completar la solicitud. Intenta más tarde.")))
        }

        //When
        cryptoViewModel.getTicker(bookName)

        //Then
        assert(cryptoViewModel.isError.value == true)
    }

    @Test
    fun `when getAskBinds return a success response`() = runTest {
        //Given
        coEvery { getAskBindUseCase(bookName) } returns flow {
            emit(Resources.Success(data = listAskBindUIMock))
        }

        //When
        cryptoViewModel.getAskBind(bookName)

        //Then
        assert(cryptoViewModel.askBindList.value == listAskBindUIMock)
    }

    @Test
    fun `when getAskBinds return a error response`() = runTest {
        //Given
        coEvery { getAskBindUseCase(bookName) } returns flow {
            emit(Resources.Error(error = BaseError(message = "Por el momento, no se pudo completar la solicitud. Intenta más tarde.")))
        }

        //When
        cryptoViewModel.getAskBind(bookName)

        //Then
        assert(cryptoViewModel.isError.value == true)
    }
}