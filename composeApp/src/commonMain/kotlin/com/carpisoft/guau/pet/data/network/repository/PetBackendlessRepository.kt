package com.carpisoft.guau.pet.data.network.repository

import com.carpisoft.guau.core.network.data.constants.NetworkConstants
import com.carpisoft.guau.core.network.data.constants.NetworkConstants.BackendlessHeaders.USER_TOKEN
import com.carpisoft.guau.core.network.data.constants.NetworkConstants.BackendlessQueryWords.LOAD_RELATIONS
import com.carpisoft.guau.core.network.data.constants.NetworkConstants.BackendlessQueryWords.OFF_SET
import com.carpisoft.guau.core.network.data.constants.NetworkConstants.BackendlessQueryWords.PAGE_SIZE
import com.carpisoft.guau.core.network.data.constants.NetworkConstants.BackendlessQueryWords.WHERE
import com.carpisoft.guau.core.network.data.model.Response
import com.carpisoft.guau.core.network.data.model.error.ResponseBackendlessError
import com.carpisoft.guau.core.network.data.model.error.ResponseError
import com.carpisoft.guau.core.network.domain.model.Resp
import com.carpisoft.guau.pet.data.network.constants.PetConstants
import com.carpisoft.guau.pet.data.network.model.PetBackendlessResponse
import com.carpisoft.guau.pet.data.network.model.PetRequest
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

class PetBackendlessRepository(private val httpClient: HttpClient) : PetPort,
    PetRepositoryHelper() {

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
                httpClient.get(urlString = "${NetworkConstants.Backendless.SERVER}${PetConstants.Backendless.PET}") {
                    contentType(ContentType.Application.Json)
                    header(key = USER_TOKEN, token)
                    url {
                        parameters.append(name = PAGE_SIZE, value = "$limit")
                        parameters.append(name = OFF_SET, value = "$page")
                        parameters.append(name = WHERE, value = "customer.center = '$centerId'")
                        parameters.append(
                            name = LOAD_RELATIONS,
                            value = "customer,breed,breed.species,customer.center"
                        )
                    }
                }.body<HttpResponse>()
            if (response.status.value in 200..299) {
                Response(isValid = true, data = response.body<List<PetBackendlessResponse>>())
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
        return processPetsBackendlessResponse(resp)
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
                httpClient.get(urlString = "${NetworkConstants.SERVER}${PetConstants.PET}") {
                    contentType(ContentType.Application.Json)
                    header(key = USER_TOKEN, token)
                    url {
                        parameters.append(name = PAGE_SIZE, value = "$limit")
                        parameters.append(name = OFF_SET, value = "$page")
                        parameters.append(
                            name = WHERE,
                            value = "customer.center = '$centerId' AND (name like '%$search%' OR customer.name like '%$search%' OR customer.lastName like '%$search%' OR customer.identification like '%$search%')"
                        )
                        parameters.append(
                            name = LOAD_RELATIONS,
                            value = "customer,breed,breed.species,customer.center"
                        )
                    }
                }.body<HttpResponse>()
            if (response.status.value in 200..299) {
                Response(isValid = true, data = response.body<List<PetBackendlessResponse>>())
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
        return processPetsBackendlessResponse(resp)
    }
}