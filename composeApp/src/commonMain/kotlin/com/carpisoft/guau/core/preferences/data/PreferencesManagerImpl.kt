package com.carpisoft.guau.core.preferences.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.carpisoft.guau.core.preferences.domain.port.PreferencesManager
import kotlinx.coroutines.flow.first

class PreferencesManagerImpl(private val dataStore: DataStore<Preferences>) : PreferencesManager {
    override suspend fun saveBoolean(key: String, value: Boolean) {
        val preferencesKey: Preferences.Key<Boolean> = booleanPreferencesKey(key)
        savePreference(preferencesKey, value)
    }

    override suspend fun saveInt(key: String, value: Int) {
        val preferencesKey: Preferences.Key<Int> = intPreferencesKey(key)
        savePreference(preferencesKey, value)
    }

    override suspend fun saveLong(key: String, value: Long) {
        val preferencesKey: Preferences.Key<Long> = longPreferencesKey(key)
        savePreference(preferencesKey, value)
    }

    override suspend fun saveString(key: String, value: String) {
        val preferencesKey: Preferences.Key<String> = stringPreferencesKey(key)
        savePreference(preferencesKey, value)
    }

    private suspend fun <T> savePreference(
        preferencesKey: Preferences.Key<T>,
        value: T
    ) {
        dataStore.edit { preferences: MutablePreferences ->
            preferences[preferencesKey] = value
        }
    }

    override suspend fun getBoolean(key: String): Boolean {
        val preferencesKey: Preferences.Key<Boolean> = booleanPreferencesKey(key)
        return getPreferences(preferencesKey) ?: false
    }

    override suspend fun getInt(key: String): Int {
        val preferencesKey: Preferences.Key<Int> = intPreferencesKey(key)
        return getPreferences(preferencesKey) ?: -1
    }

    override suspend fun getLong(key: String): Long {
        val preferencesKey: Preferences.Key<Long> = longPreferencesKey(key)
        return getPreferences(preferencesKey) ?: -1L
    }

    override suspend fun getString(key: String): String {
        val preferencesKey: Preferences.Key<String> = stringPreferencesKey(key)
        return getPreferences(preferencesKey).orEmpty()
    }

    private suspend fun <T> getPreferences(preferencesKey: Preferences.Key<T>): T? {
        val preferences: Preferences = dataStore.data.first()
        return preferences[preferencesKey]
    }
}