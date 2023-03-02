package com.carteagal.baz_android.di

import com.carteagal.baz_android.data.remote.dataSources.CryptoRemoteDataSourceImp
import com.carteagal.baz_android.data.remote.dataSources.CryptoRemoteDataSources
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ComponentsModule {

    @Binds
    abstract fun cryptoRemoteDataSourceModule(cryptoRemoteDataSourcesImp: CryptoRemoteDataSourceImp): CryptoRemoteDataSources
}