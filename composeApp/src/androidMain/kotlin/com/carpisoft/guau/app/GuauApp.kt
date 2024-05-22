package com.carpisoft.guau.app

import android.app.Application
import com.carpisoft.guau.core.di.getListModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class GuauApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@GuauApp)
            androidLogger()
            modules(modules = getListModule())
        }
    }
}