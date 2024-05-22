package com.carpisoft.guau.login.data.repository

import com.carpisoft.guau.core.network.data.constants.NetworkConstants
import com.carpisoft.guau.core.network.data.model.Response
import com.carpisoft.guau.core.network.domain.model.Resp
import com.carpisoft.guau.login.data.network.constants.LoginConstants
import com.carpisoft.guau.login.data.network.model.LoginResponse
import com.carpisoft.guau.login.data.network.model.SocialLoginRequest
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import com.carpisoft.guau.login.domain.model.LoginResp
import com.carpisoft.guau.login.domain.model.SocialLoginReq
import com.carpisoft.guau.login.domain.port.SocialLoginPort


class SocialLoginRepository(private val httpClient: HttpClient) :
    SocialLoginPort, LoginRepositoryHelper() {

    override suspend fun doSocialLogin(socialLoginReq: SocialLoginReq): Resp<LoginResp> {
        val resp = try {
            val response =
                httpClient.post(urlString = "${NetworkConstants.SERVER}${LoginConstants.SOCIAL_LOGIN}") {
                    contentType(ContentType.Application.Json)
                    setBody(
                        SocialLoginRequest(
                            socialToken = socialLoginReq.socialToken,
                            provider = socialLoginReq.provider
                        )
                    )
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