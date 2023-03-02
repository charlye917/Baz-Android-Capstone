package com.carteagal.baz_android.data.remote.repository

import android.content.Context
import com.carteagal.baz_android.data.remote.dataSources.CryptoRemoteDataSources
import com.carteagal.baz_android.data.remote.model.TickerResponse
import com.carteagal.baz_android.data.remote.model.base.BaseServiceResponse
import dagger.hilt.android.qualifiers.ApplicationContext
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class TickerRepositoryRxNetwork @Inject constructor(
    private val apiDataSource: CryptoRemoteDataSources,
    @ApplicationContext private val context: Context
) {
    fun getTickerInfoRx(book: String): Observable<BaseServiceResponse<TickerResponse>> {
        return apiDataSource.getTickerRx(book)
            .flatMap { Observable.just(it) }
    }
}