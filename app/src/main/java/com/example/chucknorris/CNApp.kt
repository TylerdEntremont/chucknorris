package com.example.chucknorris

import android.app.Application
import com.example.chucknorris.di.networkModule
import com.example.chucknorris.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class CNApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@CNApp)
            modules(listOf(networkModule, viewModelModule))
        }
    }
}