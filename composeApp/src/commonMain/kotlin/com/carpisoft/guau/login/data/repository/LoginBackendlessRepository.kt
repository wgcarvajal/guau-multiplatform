package com.carpisoft.guau.login.data.repository

import com.carpisoft.guau.core.network.data.constants.NetworkConstants
import com.carpisoft.guau.core.network.data.model.Response
import com.carpisoft.guau.core.network.data.model.error.ResponseBackendlessError
import com.carpisoft.guau.core.network.domain.model.Resp
import com.carpisoft.guau.login.data.network.constants.LoginConstants
import com.carpisoft.guau.login.data.network.model.LoginBackendlessRequest
import com.carpisoft.guau.login.data.network.model.LoginBackendlessResponse
import com.carpisoft.guau.login.domain.model.LoginReq
import com.carpisoft.guau.login.domain.model.LoginResp
import com.carpisoft.guau.login.domain.port.LoginPort
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType

class LoginBackendlessRepository(
    private val httpClient: HttpClient
) : LoginPort, LoginRepositoryHelper() {
    override suspend fun doLogin(loginReq: LoginReq): Resp<LoginResp> {
        val resp = try {
            val response =
                httpClient.post(urlString = "${NetworkConstants.Backendless.SERVER}${LoginConstants.Backendless.LOGIN}") {
                    contentType(ContentType.Application.Json)
                    setBody(
                        LoginBackendlessRequest(
                            login = loginReq.email,
                            password = loginReq.password
                        )
                    )
                }.body<HttpResponse>()
            if (response.status.value in 200..299) {
                val resp = response.body<LoginBackendlessResponse>()
                println("response = $resp")
                Response(isValid = true, data = resp)
            } else {
                val errorBody = response.body<ResponseBackendlessError>()
                println("message $errorBody")
                Response(isValid = false, error = errorBody.message, errorCode = errorBody.code)
            }

        } catch (e: Exception) {
            println("message= $e")
            Response(isValid = false, error = e.message ?: "")
        }
        return processBackendlessResponse(response = resp)
    }
}