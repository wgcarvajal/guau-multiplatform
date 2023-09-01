package login.data.repository

import core.data.preferences.IPreferences
import login.data.LoginPreferencesConstants
import login.domain.port.LoginAuthorizationPort


class LoginAuthorizationRepository (private val preferences: IPreferences) :
    LoginAuthorizationPort {

    override suspend fun saveToken(token: String) {
        preferences.saveString(key = LoginPreferencesConstants.TOKEN_KEY, value = token)
    }

    override suspend fun saveEmail(email: String) {
        preferences.saveString(key = LoginPreferencesConstants.EMAIL_KEY, value = email)
    }

    override suspend fun saveName(name: String) {
        preferences.saveString(key = LoginPreferencesConstants.NAME_KEY, value = name)
    }

    override suspend fun getToken(): String {
        return preferences.getString(LoginPreferencesConstants.TOKEN_KEY)
    }

    override suspend fun getEmail(): String {
        return preferences.getString(LoginPreferencesConstants.EMAIL_KEY)
    }

    override suspend fun getName(): String {
        return preferences.getString(LoginPreferencesConstants.NAME_KEY)
    }
}