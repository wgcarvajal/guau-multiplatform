package core.data.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

fun createDataStore(): DataStore<Preferences> = getDataStore(
    producePath = { "/Users/wilson/preferences/${dataStoreFileName}" }
)