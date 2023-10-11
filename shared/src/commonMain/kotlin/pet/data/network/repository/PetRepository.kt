package pet.data.network.repository

import core.data.network.constants.NetworkConstants
import core.data.network.model.Response
import core.data.network.model.error.ResponseError
import core.domain.model.Resp
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import pet.data.network.constants.PetConstants
import pet.data.network.model.PetRequest
import pet.domain.model.PetReq
import pet.domain.port.PetPort

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
}