package com.carpisoft.guau.login.data.repository


import com.carpisoft.guau.core.network.data.constants.NetworkConstants
import com.carpisoft.guau.core.network.data.model.Response
import com.carpisoft.guau.core.network.domain.model.Resp
import com.carpisoft.guau.login.data.network.constants.LoginConstants
import com.carpisoft.guau.login.data.network.model.LoginRequest
import com.carpisoft.guau.login.data.network.model.LoginResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import com.carpisoft.guau.login.domain.model.LoginReq
import com.carpisoft.guau.login.domain.model.LoginResp
import com.carpisoft.guau.login.domain.port.LoginPort

class LoginRepository(
    private val httpClient: HttpClient
) : LoginPort, LoginRepositoryHelper() {
    override suspend fun doLogin(loginReq: LoginReq): Resp<LoginResp> {
        val resp = try {
            val response =
                httpClient.post(urlString = "${NetworkConstants.SERVER}${LoginConstants.LOGIN_PATH}") {
                    contentType(ContentType.Application.Json)
                    setBody(LoginRequest(email = loginReq.email, password = loginReq.password))
                }.body<HttpResponse>()
            if (response.status.value in 200..299) {
                Response(isValid = true, data = response.body<LoginResponse>())
            } else {
                val errorBody = response.body<String>()
                println("message $errorBody")
                Response(isValid = false, error = errorBody)
            }

        } catch (e: Exception) {
            println("message= $e")
            Response(isValid = false, error = e.message ?: "")
        }
        return processResponse(response = resp)
    }
}