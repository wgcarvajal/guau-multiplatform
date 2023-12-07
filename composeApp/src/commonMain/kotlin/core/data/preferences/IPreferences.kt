package core.data.preferences

interface IPreferences {
    suspend fun saveBoolean(key: String, value: Boolean)
    suspend fun saveInt(key: String, value: Int)
    suspend fun saveLong(key: String, value: Long)
    suspend fun saveString(key: String, value: String)

    suspend fun getBoolean(key: String): Boolean
    suspend fun getInt(key: String): Int
    suspend fun getLong(key: String): Long
    suspend fun getString(key: String): String
}