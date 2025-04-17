package com.carpisoft.guau.login.domain.port

interface LoginAuthorizationPort {

    suspend fun saveToken(token: String)
    suspend fun saveEmail(email: String)
    suspend fun saveName(name: String)

    suspend fun saveUserId(userId: String)

    suspend fun getToken(): String
    suspend fun getEmail(): String
    suspend fun getName(): String
    suspend fun getUserId(): String
}