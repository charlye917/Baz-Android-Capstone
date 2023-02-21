package com.carteagal.baz_android.di

import com.carteagal.baz_android.data.remote.service.CryptoApiClient
import com.carteagal.baz_android.utils.Constants.BASE_URL
import com.carteagal.baz_android.utils.Constants.TIME_OUT
import com.carteagal.baz_android.utils.Constants.USER_AGENT
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BODY
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun providesHttpLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply { level = BODY }

    @Singleton
    @Provides
    fun providesOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(TIME_OUT, TimeUnit.MINUTES)
            .connectTimeout(TIME_OUT, TimeUnit.MINUTES)
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor { chain ->
                val original = chain.request()
                val requestBuilder = original.newBuilder()
                    .header(USER_AGENT, original.url.host)
                    .method(original.method, original.body)
                val request = requestBuilder.build()
                return@addInterceptor chain.proceed(request)
            }
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()

    @Singleton
    @Provides
    fun provideApiClient(retrofit: Retrofit): CryptoApiClient =
        retrofit.create(CryptoApiClient::class.java)
}