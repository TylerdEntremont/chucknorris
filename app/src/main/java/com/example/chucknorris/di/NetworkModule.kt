package com.example.chucknorris.di

import com.example.chucknorris.rest.CNAPI
import com.example.chucknorris.rest.CNRepository
import com.example.chucknorris.rest.CNRepositoryImpl
import com.example.chucknorris.viewModel.JokeViewModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {

    fun providesLoggingInterceptor() = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    fun provideMoshi() = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()

    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor) =
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()

    fun providesServiceApi(okHttpClient: OkHttpClient, moshi: Moshi) =
        Retrofit.Builder()
            .baseUrl(CNAPI.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(okHttpClient)
            .build()
            .create(CNAPI::class.java)

    single { provideMoshi() }
    single { providesLoggingInterceptor() }
    single { provideOkHttpClient(get()) }
    single { providesServiceApi(get(), get()) }
}

val viewModelModule = module {

    fun providesJokeRepo(cnapi: CNAPI): CNRepository =
        CNRepositoryImpl(cnapi)

    single { providesJokeRepo(get()) }

    viewModel { JokeViewModel(get()) }
}
