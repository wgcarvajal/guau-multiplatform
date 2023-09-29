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
import pet.data.network.model.SpeciesResponse
import pet.domain.model.SpeciesResp
import pet.domain.port.SpeciesPort


class SpeciesRepository(
    private val httpClient: HttpClient
) : SpeciesPort,
    SpeciesRepositoryHelper() {
    override suspend fun getSpecies(
        token: String
    ): Resp<List<SpeciesResp>> {

        val resp = try {
            val response =
                httpClient.get(urlString = "${NetworkConstants.SERVER}${PetConstants.SPECIES}") {
                    contentType(ContentType.Application.Json)
                    header(key = "Authorization", token)
                }.body<HttpResponse>()
            if (response.status.value in 200..299) {
                Response(isValid = true, data = response.body<List<SpeciesResponse>>())
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