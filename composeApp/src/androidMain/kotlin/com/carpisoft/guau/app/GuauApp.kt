package com.carpisoft.guau.app

import android.app.Application
import com.backendless.Backendless
import com.carpisoft.guau.core.di.getListModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class GuauApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Backendless.initApp(
            applicationContext,"A8772C77-85B7-4381-A95C-8F4DDA5B8189","E343AD9E-988F-40D2-B2D2-24CB7E15F537"
        )
        startKoin {
            androidContext(this@GuauApp)
            androidLogger()
            modules(modules = getListModule())
        }
    }
}