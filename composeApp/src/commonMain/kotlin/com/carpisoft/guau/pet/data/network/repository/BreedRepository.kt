package com.carpisoft.guau.pet.data.network.repository

import com.carpisoft.guau.core.network.data.constants.NetworkConstants
import com.carpisoft.guau.core.network.data.model.Response
import com.carpisoft.guau.core.network.data.model.error.ResponseError
import com.carpisoft.guau.core.network.domain.model.Resp
import com.carpisoft.guau.pet.data.network.constants.PetConstants
import com.carpisoft.guau.pet.data.network.model.BreedResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import com.carpisoft.guau.pet.domain.model.BreedResp
import com.carpisoft.guau.pet.domain.port.BreedPort

class BreedRepository(private val httpClient: HttpClient) : BreedPort, BreedRepositoryHelper() {

    override suspend fun getBreedsBySpeciesIdWithPaginationAndSort(
        token: String,
        speciesId: Int,
        page: Int,
        limit: Int
    ): Resp<List<BreedResp>> {
        val resp = try {
            val response =
                httpClient.get(urlString = "${NetworkConstants.SERVER}${PetConstants.BREED}/$speciesId/$page/$limit") {
                    contentType(ContentType.Application.Json)
                    header(key = "Authorization", token)
                }.body<HttpResponse>()
            if (response.status.value in 200..299) {
                Response(isValid = true, data = response.body<List<BreedResponse>>())
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

    override suspend fun getBreedsBySpeciesIdAndNameWithPaginationAndSort(
        token: String,
        speciesId: Int,
        name: String,
        page: Int,
        limit: Int
    ): Resp<List<BreedResp>> {
        val resp = try {
            val response =
                httpClient.get(urlString = "${NetworkConstants.SERVER}${PetConstants.BREED}/$speciesId/$name/$page/$limit") {
                    contentType(ContentType.Application.Json)
                    header(key = "Authorization", token)
                }.body<HttpResponse>()
            if (response.status.value in 200..299) {
                Response(isValid = true, data = response.body<List<BreedResponse>>())
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