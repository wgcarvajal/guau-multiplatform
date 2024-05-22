package com.carpisoft.guau.core.datastore.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

fun createDataStore(context: Context): DataStore<Preferences> = getDataStore(
    producePath = { context.filesDir.resolve(dataStoreFileName).absolutePath }
)