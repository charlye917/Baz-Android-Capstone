package com.carteagal.baz_android.di

import android.content.Context
import androidx.room.Room
import com.carteagal.baz_android.data.local.database.CryptoDataBase
import com.carteagal.baz_android.utils.Constants
import com.carteagal.baz_android.utils.Constants.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Singleton
    @Provides
    fun providerRoom(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, CryptoDataBase::class.java, DATABASE_NAME)
            .build()

    @Singleton
    @Provides
    fun provideAvailableBookDao(database: CryptoDataBase) = database.getAvailableBooksDao()

    @Singleton
    @Provides
    fun provideOrderBookDao(database: CryptoDataBase) = database.getOrderBooks()

    @Singleton
    @Provides
    fun provideTickerDao(database: CryptoDataBase) = database.getTickerDao()
}