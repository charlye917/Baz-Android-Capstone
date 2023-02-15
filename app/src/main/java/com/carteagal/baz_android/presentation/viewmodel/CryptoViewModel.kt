package com.carteagal.baz_android.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carteagal.baz_android.data.local.repository.CryptoLocalRepository
import com.carteagal.baz_android.domain.model.AvailableBookUI
import com.carteagal.baz_android.data.remote.network.Resources.Error
import com.carteagal.baz_android.data.remote.network.Resources.Loading
import com.carteagal.baz_android.data.remote.network.Resources.Success
import com.carteagal.baz_android.domain.model.AskBindUI
import com.carteagal.baz_android.domain.model.TickerUI
import com.carteagal.baz_android.domain.useCase.GetAvailableBooksUseCase
import com.carteagal.baz_android.domain.useCase.GetAskBindUseCase
import com.carteagal.baz_android.domain.useCase.GetTickerUserCase
import com.carteagal.baz_android.utils.TypeSorts
import com.carteagal.baz_android.utils.TypeSorts.SORT_MAX
import com.carteagal.baz_android.utils.TypeSorts.SORT_MIN
import com.carteagal.baz_android.utils.TypeSorts.SORT_NAME_AZ
import com.carteagal.baz_android.utils.TypeSorts.SORT_NAME_ZA
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

    private val _isError = MutableLiveData(false)
    val isError: LiveData<Boolean> get() = _isError

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
                        _loading.postValue(false)
                        _isError.postValue(false)
                        localRepository.insertAllBooks(it.data)
                        _availableBooks.postValue(it.data)
                    }
                    is Error -> {
                        _loading.postValue(false)
                        if(localData.isNotEmpty()){
                            _isError.postValue(false)
                            _availableBooks.postValue(localData)
                        }
                        else
                            _isError.postValue(true)
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
                        _isError.postValue(false)
                        _loading.postValue(false)
                        localRepository.insertTicker(it.data)
                        _ticker.postValue(it.data)
                    }
                    is Error -> {
                        _loading.postValue(false)
                        if(!localData.fullName.isNullOrBlank()){
                            _isError.postValue(false)
                            _ticker.postValue(localData)
                        }else
                            _isError.postValue(true)
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
                        _loading.postValue(false)
                        _isError.postValue(false)
                        localRepository.insertAskBind(it.data, book)
                        _askBindList.postValue(it.data)
                    }
                    is Error -> {
                        _loading.postValue(false)
                        if(localData.isNotEmpty()){
                            _isError.postValue(false)
                            _askBindList.postValue(localData)
                        }
                        else
                            _isError.postValue(true)
                    }
                }
            }
        }
    }

    fun orderListBooks(sort: TypeSorts){
        val sortList = when(sort){
            SORT_NAME_AZ -> { _availableBooks.value?.sortedBy { it.name } }
            SORT_NAME_ZA -> { _availableBooks.value?.sortedByDescending { it.name } }
            SORT_MAX -> { _availableBooks.value?.sortedByDescending { it.maxPrice } }
            SORT_MIN -> { _availableBooks.value?.sortedBy { it.minPrice } }
        }
        _availableBooks.postValue(sortList!!)
    }
}
