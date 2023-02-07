package com.carteagal.baz_android.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carteagal.baz_android.domain.model.AvailableBookUI
import com.carteagal.baz_android.data.model.orderBook.OrderBookResponse
import com.carteagal.baz_android.data.model.TickerResponse
import com.carteagal.baz_android.data.remote.network.Resources
import com.carteagal.baz_android.data.remote.network.Resources.Error
import com.carteagal.baz_android.data.remote.network.Resources.Loading
import com.carteagal.baz_android.data.remote.network.Resources.Success
import com.carteagal.baz_android.domain.model.TickerUI
import com.carteagal.baz_android.domain.useCase.GetAvailableBooksUseCase
import com.carteagal.baz_android.domain.useCase.GetOrderBookUseCase
import com.carteagal.baz_android.domain.useCase.GetTickerUserCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CryptoViewModel @Inject constructor(
    private val getAvailableBooksUseCase: GetAvailableBooksUseCase,
    private val getOrderBookUseCase: GetOrderBookUseCase,
    private val getTickerUserCase: GetTickerUserCase
): ViewModel(){

    private val _availableBooks = MutableLiveData<Resources<List<AvailableBookUI>>>()
    private val _orderBooks = MutableLiveData<Resources<List<OrderBookResponse>?>>()
    private val _ticker = MutableLiveData<Resources<TickerUI>>()

    val availableBooks: LiveData<Resources<List<AvailableBookUI>>> get() = _availableBooks
    val orderBooks: LiveData<Resources<List<OrderBookResponse>?>> get() = _orderBooks
    val ticker: LiveData<Resources<TickerUI>> get() = _ticker

    fun getAvailableBooks(){
        _availableBooks.postValue(Loading)
        viewModelScope.launch {
            getAvailableBooksUseCase().collect{
                _availableBooks.postValue(it)
            }
        }
    }

    fun getTicker(book: String){
        _ticker.postValue(Loading)
        viewModelScope.launch {
            getTickerUserCase(book).collect{
                _ticker.postValue(it)
            }
        }
    }

    /*fun getOrderBooks(book: String) = viewModelScope.launch {
        _loading.postValue(true)
        val result = when(val data = getOrderBookUseCase(book)){
            is Success -> Success(data.data)
            is Error -> Error(data.message)
            else -> Error(data.message)
        }
        _orderBooks.postValue(result)
        _loading.postValue(false)
    }*/

    /*fun getTicker(book: String) = viewModelScope.launch {
        _loading.postValue(true)
        val result = when(val data = getTickerUserCase(book)){
            is Success -> Success(data.data)
            is Error -> Error(data.message)
            else -> Error(data.message)
        }
        _ticker.postValue(result)
        _loading.postValue(false)
    }*/
}