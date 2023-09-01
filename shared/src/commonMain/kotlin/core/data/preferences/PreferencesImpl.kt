package core.data.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first

class PreferencesImpl(private val dataStore: DataStore<Preferences>) : IPreferences {
    override suspend fun saveBoolean(key: String, value: Boolean) {
        val preferencesKey = booleanPreferencesKey(key)
        savePreference(preferencesKey, value)
    }

    override suspend fun saveInt(key: String, value: Int) {
        val preferencesKey = intPreferencesKey(key)
        savePreference(preferencesKey, value)
    }

    override suspend fun saveString(key: String, value: String) {
        val preferencesKey = stringPreferencesKey(key)
        savePreference(preferencesKey, value)
    }

    private suspend fun <T> savePreference(
        preferencesKey: androidx.datastore.preferences.core.Preferences.Key<T>,
        value: T
    ) {
        dataStore.edit { preferences ->
            preferences[preferencesKey] = value
        }
    }

    override suspend fun getBoolean(key: String): Boolean {
        val preferencesKey = booleanPreferencesKey(key)
        return getPreferences(preferencesKey) ?: false
    }

    override suspend fun getInt(key: String): Int {
        val preferencesKey = intPreferencesKey(key)
        return getPreferences(preferencesKey) ?: -1
    }

    override suspend fun getString(key: String): String {
        val preferencesKey = stringPreferencesKey(key)
        return getPreferences(preferencesKey).orEmpty()
    }

    private suspend fun <T> getPreferences(preferencesKey: androidx.datastore.preferences.core.Preferences.Key<T>): T? {
        val preferences = dataStore.data.first()
        return preferences[preferencesKey]
    }
}