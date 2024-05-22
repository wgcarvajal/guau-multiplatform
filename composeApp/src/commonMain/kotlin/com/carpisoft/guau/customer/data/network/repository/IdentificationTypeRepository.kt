package com.carpisoft.guau.customer.data.network.repository

import com.carpisoft.guau.core.network.data.constants.NetworkConstants
import com.carpisoft.guau.core.network.data.model.Response
import com.carpisoft.guau.core.network.data.model.error.ResponseError
import com.carpisoft.guau.core.network.domain.model.Resp
import com.carpisoft.guau.customer.data.network.constants.CustomerConstants
import com.carpisoft.guau.customer.data.network.model.IdentificationTypeResponse
import com.carpisoft.guau.customer.domain.model.IdentificationTypeResp
import com.carpisoft.guau.customer.domain.port.IdentificationTypePort
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType

class IdentificationTypeRepository(private val httpClient: HttpClient) : IdentificationTypePort,
    IdentificationTypeRepositoryHelper() {

    override suspend fun getAllIdentificationType(token: String): Resp<List<IdentificationTypeResp>> {
        val resp = try {
            val response =
                httpClient.get(urlString = "${NetworkConstants.SERVER}${CustomerConstants.IDENTIFICATION_TYPES}") {
                    contentType(ContentType.Application.Json)
                    header(key = "Authorization", token)
                }.body<HttpResponse>()
            if (response.status.value in 200..299) {
                Response(isValid = true, data = response.body<List<IdentificationTypeResponse>>())
            } else {
                val errorBody = response.body<ResponseError>()
                println("message $errorBody")
                Response(
                    isValid = false,
                    error = errorBody.message,
                    errorCode = errorBody.errorCode
                )
            }

        } catch (e: Exception) {
            println("message= $e")
            Response(isValid = false, error = e.message ?: "")
        }
        return processResponse(resp)
    }
}