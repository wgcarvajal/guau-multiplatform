package pet.data.network.repository

import core.data.network.constants.NetworkConstants
import core.data.network.model.Response
import core.data.network.model.error.ResponseError
import core.domain.model.Resp
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import pet.data.network.constants.PetConstants
import pet.data.network.model.BreedResponse
import pet.domain.model.BreedResp
import pet.domain.port.BreedPort

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

}