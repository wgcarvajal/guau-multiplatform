package com.carpisoft.guau.pet.data.network.repository

import com.carpisoft.guau.core.network.data.constants.NetworkConstants
import com.carpisoft.guau.core.network.data.model.Response
import com.carpisoft.guau.core.network.data.model.error.ResponseError
import com.carpisoft.guau.core.network.domain.model.Resp
import com.carpisoft.guau.pet.data.network.constants.PetConstants
import com.carpisoft.guau.pet.data.network.model.PetRequest
import com.carpisoft.guau.pet.data.network.model.PetResponse
import com.carpisoft.guau.pet.domain.model.PetReq
import com.carpisoft.guau.pet.domain.model.PetResp
import com.carpisoft.guau.pet.domain.port.PetPort
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType

class PetRepository(private val httpClient: HttpClient) : PetPort, PetRepositoryHelper() {

    override suspend fun save(token: String, petReq: PetReq): Resp<Long> {
        val resp = try {
            val response =
                httpClient.post(urlString = "${NetworkConstants.SERVER}${PetConstants.PET}${PetConstants.SAVE_PET}") {
                    contentType(ContentType.Application.Json)
                    header(key = "Authorization", token)
                    setBody(
                        PetRequest(
                            date = petReq.date,
                            name = petReq.name,
                            description = petReq.description,
                            breed = petReq.breed,
                            customer = petReq.customer,
                            gender = petReq.gender
                        )
                    )
                }.body<HttpResponse>()
            if (response.status.value in 200..299) {
                Response(isValid = true, data = response.body<Long>())
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

    override suspend fun getPetsByCenterIdWithPaginationAndSort(
        token: String,
        centerId: String,
        page: Int,
        limit: Int
    ): Resp<List<PetResp>> {
        val resp = try {
            val response =
                httpClient.get(urlString = "${NetworkConstants.SERVER}${PetConstants.PET}/$centerId/$page/$limit") {
                    contentType(ContentType.Application.Json)
                    header(key = "Authorization", token)
                }.body<HttpResponse>()
            if (response.status.value in 200..299) {
                Response(isValid = true, data = response.body<List<PetResponse>>())
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
        return processPetsResponse(resp)
    }

    override suspend fun getPetsByCenterIdAndSearchWithPaginationAndSort(
        token: String,
        centerId: String,
        search: String,
        page: Int,
        limit: Int
    ): Resp<List<PetResp>> {
        val resp = try {
            val response =
                httpClient.get(urlString = "${NetworkConstants.SERVER}${PetConstants.PET}/$centerId/$search/$page/$limit") {
                    contentType(ContentType.Application.Json)
                    header(key = "Authorization", token)
                }.body<HttpResponse>()
            if (response.status.value in 200..299) {
                Response(isValid = true, data = response.body<List<PetResponse>>())
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
        return processPetsResponse(resp)
    }
}