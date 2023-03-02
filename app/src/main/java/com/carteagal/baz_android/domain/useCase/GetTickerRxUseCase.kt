package com.carteagal.baz_android.domain.useCase

import android.annotation.SuppressLint
import com.carteagal.baz_android.data.local.repository.CryptoLocalRepository
import com.carteagal.baz_android.data.remote.model.base.BaseError
import com.carteagal.baz_android.data.remote.network.Resources
import com.carteagal.baz_android.data.remote.network.Resources.Error
import com.carteagal.baz_android.data.remote.network.Resources.Success
import com.carteagal.baz_android.data.remote.repository.TickerRepositoryRxNetwork
import com.carteagal.baz_android.domain.mapper.tickerMapper
import com.carteagal.baz_android.domain.model.TickerUI
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class GetTickerRxUseCase @Inject constructor(
    private val tickerRepositoryNetwork: TickerRepositoryRxNetwork,
    private val localRepository: CryptoLocalRepository
) {

    @SuppressLint("CheckResult")
    operator fun invoke(book: String): Resources<TickerUI> {
        val localData = localRepository.getTickerRxUI(book)
        var resources: Resources<TickerUI> = Resources.Loading
        tickerRepositoryNetwork
            .getTickerInfoRx(book)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                resources = when{
                    it.success -> {
                        localRepository.insertTickerRx(tickerMapper(it.result))
                        Success(data = tickerMapper(it.result))
                    }
                    localData.fullName != null -> {
                        Success(data = localData)
                    }
                    else -> {
                        Error(BaseError(code = it.error!!.code, message = it.error.message))
                    }
                }
            },{
                resources = Error(BaseError(message = it.message.toString()))
            })
        return resources
    }
}