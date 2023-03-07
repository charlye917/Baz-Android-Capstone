package com.carteagal.baz_android.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carteagal.baz_android.data.remote.network.CheckInternetConnection
import com.carteagal.baz_android.data.remote.network.Resources.Error
import com.carteagal.baz_android.data.remote.network.Resources.Loading
import com.carteagal.baz_android.data.remote.network.Resources.Success
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
import com.carteagal.baz_android.utils.TypeSorts
import com.carteagal.baz_android.utils.TypeSorts.SORT_MAX
import com.carteagal.baz_android.utils.TypeSorts.SORT_MIN
import com.carteagal.baz_android.utils.TypeSorts.SORT_NAME_AZ
import com.carteagal.baz_android.utils.TypeSorts.SORT_NAME_ZA
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CryptoViewModel @Inject constructor(
    private val getAvailableBooksUseCase: GetAvailableBooksUseCase,
    private val getAvailableBoookLocalUseCase: GetAvailableBookLocalUseCase,
    private val getAskBindUseCase: GetAskBindUseCase,
    private val getAskBindLocalUseCase: GetAskBindLocalUseCase,
    private val getTickerUserCase: GetTickerUserCase,
    private val getTickerLocalUseCase: GetTickerLocalUseCase,
    private val getTickerRxUseCase: GetTickerRxUseCase,
    @ApplicationContext private val context: Context
): ViewModel(){

    private val _loading = MutableLiveData(true)
    val loading: LiveData<Boolean> get() = _loading

    private val _isError = MutableLiveData(false)
    val isError: LiveData<Boolean> get() = _isError

    private val _isInternetConnection = MutableLiveData<Boolean>()
    val isInternetConnection: LiveData<Boolean> get() = _isInternetConnection

    private val _availableBooks = MutableLiveData<List<AvailableBookUI>>()
    val availableBooks: LiveData<List<AvailableBookUI>> get() = _availableBooks

    private val _ticker = MutableLiveData<TickerUI>()
    val ticker: LiveData<TickerUI> get() = _ticker

    private val _askBindList = MutableLiveData<List<AskBindUI>>()
    val askBindList: LiveData<List<AskBindUI>> get() = _askBindList

    fun getAvailableBooks(){
        viewModelScope.launch{
            val internetConnection = CheckInternetConnection.hasInternetConnection(context)
            _isInternetConnection.value = internetConnection
            if(internetConnection)
                getAvailableBooksNetwork()
            else
                getAvailableBooksLocal()
        }
    }

    fun getBookDetailData(book: String){
        viewModelScope.launch {
            val internetConnection = CheckInternetConnection.hasInternetConnection(context)
            _isInternetConnection.value = internetConnection
            if(internetConnection){
                getTickerNetwork(book)
                getAskBindNetwork(book)
                //getTickerRx(book)
            } else{
                getTickerLocal(book)
                getAskBindLocal(book)
            }
        }
    }

    private suspend fun getAvailableBooksNetwork(){
        _loading.postValue(true)
        getAvailableBooksUseCase().collect {
            when (it) {
                is Loading -> {
                    _loading.postValue(true)
                }
                is Success -> {
                    _loading.postValue(false)
                    _isError.postValue(false)
                    _availableBooks.postValue(it.data)
                    getAvailableBoookLocalUseCase.insertAllBooks(it.data)
                }
                is Error -> {
                    _loading.postValue(false)
                    getAvailableBooksLocal()
                }
            }
        }
    }

    private suspend fun getAskBindNetwork(book: String){
        _loading.postValue(true)
        getAskBindUseCase(book).collect {
            when (it) {
                is Loading -> {
                    _loading.postValue(true)
                }
                is Success -> {
                    _loading.postValue(false)
                    _isError.postValue(false)
                    _askBindList.postValue(it.data)
                    getAskBindLocalUseCase.insertAskBindBooks(it.data, book)
                }
                is Error -> {
                    _loading.postValue(false)
                    _isError.postValue(true)
                }
            }
        }
    }

    private suspend fun getTickerNetwork(book: String){
        viewModelScope.launch {
            _loading.postValue(true)
            getTickerUserCase(book).collect {
                when (it) {
                    is Loading -> {
                        _loading.postValue(true)
                    }
                    is Success -> {
                        _isError.postValue(false)
                        _loading.postValue(false)
                        _ticker.postValue(it.data)
                        getTickerLocalUseCase.insertTickerLocal(it.data)

                    }
                    is Error -> {
                        _loading.postValue(false)
                        _isError.postValue(true)
                    }
                }
            }
        }
    }

    private suspend fun getTickerRx(book: String){
        _loading.postValue(true)
        getTickerRxUseCase(book).let {
            when (it) {
                is Loading -> {
                    _loading.postValue(true)
                }
                is Success -> {
                    _isError.postValue(false)
                    _loading.postValue(false)
                    _ticker.postValue(it.data)
                    getTickerLocalUseCase.insertTickerLocal(it.data)
                }
                is Error -> {
                    _loading.postValue(false)
                    _isError.postValue(true)
                }
            }
        }
    }

    private suspend fun getAvailableBooksLocal(){
        val data = getAvailableBoookLocalUseCase.getAllLocalBooks()
        if(data.isNotEmpty()){
            _isError.postValue(false)
            _availableBooks.postValue(data)
        } else
            _isError.postValue(true)
    }

    private suspend fun getAskBindLocal(book: String){
        val data = getAskBindLocalUseCase.allAskBindBooks(book)
        if(data.isNotEmpty()){
            _isError.postValue(false)
            _askBindList.postValue(data)
        } else
            _isError.postValue(true)
    }

    private suspend fun getTickerLocal(book: String){
        val data = getTickerLocalUseCase.getTickerLocal(book)
        if(data.fullName != null){
            _isError.postValue(false)
            _ticker.postValue(data)
        } else{
            _isError.postValue(true)
        }
    }

    fun orderListBooks(sort: TypeSorts){
        val sortList = when(sort){
            SORT_NAME_AZ -> { _availableBooks.value?.sortedBy { it.name } }
            SORT_NAME_ZA -> { _availableBooks.value?.sortedByDescending { it.name } }
            SORT_MAX -> { _availableBooks.value?.sortedByDescending { it.maxPrice } }
            SORT_MIN -> { _availableBooks.value?.sortedBy { it.minPrice } }
        }
        _availableBooks.postValue(sortList ?: listOf())
    }

    fun filterListBooks(book: String) = _availableBooks.value?.filter {
        it.name?.uppercase()?.contains(book) == true
    } ?: listOf()
}
