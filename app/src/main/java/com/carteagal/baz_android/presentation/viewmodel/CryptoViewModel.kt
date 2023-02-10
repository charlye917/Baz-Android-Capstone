package com.carteagal.baz_android.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carteagal.baz_android.data.local.repository.CryptoLocalRepository
import com.carteagal.baz_android.domain.model.AvailableBookUI
import com.carteagal.baz_android.data.remote.model.OrderBookResponse
import com.carteagal.baz_android.data.remote.network.Resources.Error
import com.carteagal.baz_android.data.remote.network.Resources.Loading
import com.carteagal.baz_android.data.remote.network.Resources.Success
import com.carteagal.baz_android.domain.model.AskBindUI
import com.carteagal.baz_android.domain.model.TickerUI
import com.carteagal.baz_android.domain.useCase.GetAvailableBooksUseCase
import com.carteagal.baz_android.domain.useCase.GetAskBindUseCase
import com.carteagal.baz_android.domain.useCase.GetTickerUserCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CryptoViewModel @Inject constructor(
    private val getAvailableBooksUseCase: GetAvailableBooksUseCase,
    private val getAskBindUseCase: GetAskBindUseCase,
    private val getTickerUserCase: GetTickerUserCase,
    private val localRepository: CryptoLocalRepository
): ViewModel(){

    private val _loading = MutableLiveData(true)
    val loading: LiveData<Boolean> get() = _loading

    private val _availableBooks = MutableLiveData<List<AvailableBookUI>>()
    val availableBooks: LiveData<List<AvailableBookUI>> get() = _availableBooks

    private val _ticker = MutableLiveData<TickerUI>()
    val ticker: LiveData<TickerUI> get() = _ticker

    private val _askBindList = MutableLiveData<List<AskBindUI>>()
    val askBindList: LiveData<List<AskBindUI>> get() = _askBindList

    fun getAvailableBooks(){
        viewModelScope.launch {
            val localData = localRepository.getAllBooks()
            _loading.postValue(true)
            getAvailableBooksUseCase().collect{
                when(it){
                    is Loading -> {
                        _loading.postValue(true)
                    }
                    is Success -> {
                        localRepository.insertAllBooks(it.data)
                        _availableBooks.postValue(it.data)
                        _loading.postValue(false)
                    }
                    is Error -> {
                        if(localData.isNotEmpty())
                            _availableBooks.postValue(localData)
                        _loading.postValue(false)
                    }
                }
            }
        }
    }

    fun getTicker(book: String){
        viewModelScope.launch {
            val localData = localRepository.getTickerUI(book)
            _loading.postValue(true)
            getTickerUserCase(book).collect{
                when(it){
                    is Loading -> {
                        _loading.postValue(true)
                    }
                    is Success -> {
                        localRepository.insertTicker(it.data)
                        _ticker.postValue(it.data)
                        _loading.postValue(false)
                    }
                    is Error -> {
                        _loading.postValue(false)
                        if(!localData.fullName.isNullOrBlank())
                            _ticker.postValue(localData)
                    }
                }
            }
        }
    }

    fun getAskBind(book: String){
        viewModelScope.launch {
            val localData = localRepository.getAllAskBind(book)
            _loading.postValue(true)
            getAskBindUseCase(book).collect{
                when(it){
                    is Loading -> {
                        _loading.postValue(true)
                    }
                    is Success -> {
                        localRepository.insertAskBind(it.data, book)
                        _askBindList.postValue(it.data)
                        _loading.postValue(false)
                    }
                    is Error -> {
                        _loading.postValue(false)
                        if(!localData.isNullOrEmpty())
                            _askBindList.postValue(localData)
                    }
                }
            }
        }
    }
}
