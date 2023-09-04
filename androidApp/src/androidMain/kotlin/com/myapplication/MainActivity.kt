package com.myapplication

import MainView
import android.os.Bundle
import androidx.compose.ui.platform.LocalContext
import core.data.preferences.createDataStore
import moe.tlaster.precompose.lifecycle.PreComposeActivity
import moe.tlaster.precompose.lifecycle.setContent

class MainActivity : PreComposeActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent() {
            MainView(dataStore = createDataStore(context = LocalContext.current.applicationContext), finishCallback = {
                finish()
            })
        }
    }
}