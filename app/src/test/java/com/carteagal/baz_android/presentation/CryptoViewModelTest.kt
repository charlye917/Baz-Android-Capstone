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
import com.carteagal.baz_android.utils.TypeAskBid.ASKS
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
import org.junit.Before
import org.junit.Rule
import org.junit.Test

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

    val availableBooksUI = listOf(
        AvailableBookUI(
            fullName = "aave_usd",
            name = "Aave",
            typeMoney =  "USD",
            imageUrl = "https://raw.githubusercontent.com/spothq/cryptocurrency-icons/master/128/color/aave.png",
            maxPrice = 1000.0,
            minPrice = 3.0,
            amount = 200000.0
        ),
        AvailableBookUI(
            fullName = "algo_usd",
            name = "Algorand",
            typeMoney =  "USD",
            imageUrl = "https://raw.githubusercontent.com/spothq/cryptocurrency-icons/master/128/color/algo.png",
            maxPrice = 3.0,
            minPrice = 0.008,
            amount = 6.0E7
        )
    )

    val tickerUI = TickerUI(
        fullName = "aave_usd",
        bookName = "Aave",
        typeMoney = "USD",
        price = 88.76,
        highPrice = 90.53,
        lowPrice = 83.64,
        ask = 88.66,
        bind = 88.84,
        lastModification = "jueves 16 de febrero 2023",
        urlBook = "https://raw.githubusercontent.com/spothq/cryptocurrency-icons/master/128/color/aave.png"
    )

    val listAskBindUI = listOf(
        AskBindUI(
            book = "aave_usd",
            amount = 0.01118468,
            price = 89.41,
            type = ASKS,
        ),
        AskBindUI(
            book = "aave_usd",
            amount = 11.90850234,
            price = 89.27,
            type = ASKS,
        ),
        AskBindUI(
            book = "aave_usd",
            amount = 5.871958,
            price = 89.26,
            type = ASKS,
        )
    )

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
            emit(Resources.Success(data = availableBooksUI))
        }

        //When
        cryptoViewModel.getAvailableBooks()

        //Then
        assert(cryptoViewModel.availableBooks.value == availableBooksUI)
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
        assert(cryptoViewModel.isError.value == true)
    }

    @Test
    fun `when getTicker return a success response`() = runTest {
        //Given
        coEvery { getTickerUserCase(bookName) } returns flow {
            emit(Resources.Success(data = tickerUI))
        }

        //When
        cryptoViewModel.getTicker(bookName)

        //Then
        assert(cryptoViewModel.ticker.value == tickerUI)
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
            emit(Resources.Success(data = listAskBindUI))
        }

        //When
        cryptoViewModel.getAskBind(bookName)

        //Then
        assert(cryptoViewModel.askBindList.value == listAskBindUI)
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