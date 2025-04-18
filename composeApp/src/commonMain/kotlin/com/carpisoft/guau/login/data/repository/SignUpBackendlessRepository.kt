package com.carpisoft.guau.login.data.repository

import com.carpisoft.guau.core.network.data.constants.NetworkConstants
import com.carpisoft.guau.core.network.data.model.Response
import com.carpisoft.guau.core.network.data.model.error.ResponseBackendlessError
import com.carpisoft.guau.core.network.domain.model.Resp
import com.carpisoft.guau.login.data.network.constants.LoginConstants
import com.carpisoft.guau.login.data.network.model.SignUpRequest
import com.carpisoft.guau.login.domain.model.SignUpReq
import com.carpisoft.guau.login.domain.port.SignUpPort
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType

class SignUpBackendlessRepository(private val httpClient: HttpClient) : SignUpPort {

    override suspend fun doRegister(signUpReq: SignUpReq): Resp<Any> {
        val resp = try {
            val response =
                httpClient.post(urlString = "${NetworkConstants.Backendless.SERVER}${LoginConstants.Backendless.USER_REGISTER}") {
                    contentType(ContentType.Application.Json)
                    setBody(
                        SignUpRequest(
                            email = signUpReq.email,
                            password = signUpReq.password,
                            lastName = signUpReq.lastName,
                            name = signUpReq.name
                        )
                    )
                }.body<HttpResponse>()
            if (response.status.value in 200..299) {
                Response(isValid = true, data = null)
            } else {
                val errorBody = response.body<ResponseBackendlessError>()
                println("message $errorBody")
                Response(
                    isValid = false,
                    error = errorBody.message,
                    errorCode = errorBody.code
                )
            }

        } catch (e: Exception) {
            println("message= $e")
            Response(isValid = false, error = e.message ?: "")
        }
        return Resp(resp.isValid, resp.error, resp.param, resp.errorCode, null)
    }

}