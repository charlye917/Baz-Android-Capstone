package com.carteagal.baz_android.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carteagal.baz_android.domain.model.AvailableBookUI
import com.carteagal.baz_android.data.model.OrderBookResponse
import com.carteagal.baz_android.data.remote.network.Resources
import com.carteagal.baz_android.data.remote.network.Resources.Loading
import com.carteagal.baz_android.domain.model.OrderBookUI
import com.carteagal.baz_android.domain.model.TickerUI
import com.carteagal.baz_android.domain.useCase.GetAvailableBooksUseCase
import com.carteagal.baz_android.domain.useCase.GetOrderBookUseCase
import com.carteagal.baz_android.domain.useCase.GetTickerUserCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CryptoViewModel @Inject constructor(
    private val getAvailableBooksUseCase: GetAvailableBooksUseCase,
    private val getOrderBookUseCase: GetOrderBookUseCase,
    private val getTickerUserCase: GetTickerUserCase
): ViewModel(){

    private val _availableBooks = MutableLiveData<Resources<List<AvailableBookUI>>>()
    val availableBooks: LiveData<Resources<List<AvailableBookUI>>> get() = _availableBooks

    private val _orderBooks = MutableLiveData<Resources<OrderBookResponse>>()
    val orderBooks: LiveData<Resources<OrderBookResponse>> get() = _orderBooks

    private val _ticker = MutableLiveData<Resources<TickerUI>>()
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

    fun getOrderBook(book: String){
        _orderBooks.postValue(Loading)
        viewModelScope.launch {
            getOrderBookUseCase(book).collect{
                _orderBooks.postValue(it)
            }
        }
    }
}