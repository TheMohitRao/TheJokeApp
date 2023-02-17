package com.themohitrao.core_network.di

import com.themohitrao.core_network.api.JokeApiService
import com.themohitrao.core_network.data_source.JokeDataSource
import com.themohitrao.core_network.data_source.JokeDataSourceImpl
import com.themohitrao.core_network.util.NetworkUtil
import com.themohitrao.core_network.util.RxSingleSchedulers
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single { Dispatchers.IO }
    single { createBasicAuthService() }
    single { RxSingleSchedulers.DEFAULT }
    single { NetworkUtil(androidContext()) }
    singleOf(::JokeDataSourceImpl) { bind<JokeDataSource>() }
}

private fun createBasicAuthService(): JokeApiService {
    val retrofit = Retrofit.Builder()
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create()).client(basicOkHttpClient())
        .baseUrl("https://geek-jokes.sameerkumar.website/")
        .build()
    return retrofit.create(JokeApiService::class.java)
}

private fun basicOkHttpClient() = OkHttpClient.Builder().addInterceptor(httpInterceptor()).build()

private fun httpInterceptor() = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.BODY
}










