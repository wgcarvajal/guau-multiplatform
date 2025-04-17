package com.carpisoft.guau.login.data.network.constants

class LoginConstants {
    companion object{
        const val LOGIN_PATH = "/login"
        const val SOCIAL_LOGIN = "/slogin"
        const val BASIC_REGISTER = "/api/v1/person/register"
    }

    object Backendless{
        const val LOGIN = "users/login"
        const val USER_REGISTER = "users/register"
    }
}