package customer.data.network.repository

import core.data.network.constants.NetworkConstants
import core.data.network.model.Response
import core.data.network.model.error.ResponseError
import core.domain.model.Resp
import customer.data.network.constants.CustomerConstants
import customer.data.network.model.CustomerResponse
import customer.data.network.model.RegisterCustomerRequest
import customer.data.network.model.RegisterCustomerResponse
import customer.domain.model.CustomerResp
import customer.domain.model.RegisterCustomerReq
import customer.domain.model.RegisterCustomerResp
import customer.domain.port.CustomerPort
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import pet.data.network.constants.PetConstants
import pet.data.network.model.BreedResponse

class CustomerRepository(private val httpClient: HttpClient) : CustomerPort,
    CustomerRepositoryHelper() {

    override suspend fun save(token: String, customerReq: RegisterCustomerReq): Resp<RegisterCustomerResp> {
        val resp = try {
            val response =
                httpClient.post(urlString = "${NetworkConstants.SERVER}${CustomerConstants.REGISTER_CUSTOMER}") {
                    contentType(ContentType.Application.Json)
                    header(key = "Authorization", token)
                    setBody(
                        RegisterCustomerRequest(
                            center = customerReq.center,
                            identificationType = customerReq.identificationType,
                            identification = customerReq.identification,
                            name = customerReq.name,
                            lastName = customerReq.lastName,
                            email = customerReq.email,
                            address = customerReq.address,
                            phone = customerReq.phone
                        )
                    )
                }.body<HttpResponse>()
            if (response.status.value in 200..299) {
                Response(isValid = true, data = response.body<RegisterCustomerResponse>())
            } else {
                val errorBody = response.body<ResponseError>()
                Response(
                    isValid = false,
                    error = errorBody.message,
                    param = errorBody.param,
                    errorCode = errorBody.errorCode
                )
            }
        } catch (e: Exception) {
            Response(isValid = false, error = e.message ?: "")
        }
        return processResponse(resp)
    }

    override suspend fun getCustomersByCenterIdAndNameWithPaginationAndSort(
        token: String,
        centerId: Int,
        search: String,
        page: Int,
        limit: Int
    ): Resp<List<CustomerResp>> {
        val resp = try {
            val response =
                httpClient.get(urlString = "${NetworkConstants.SERVER}${CustomerConstants.CUSTOMERS}/$centerId/$search/$page/$limit") {
                    contentType(ContentType.Application.Json)
                    header(key = "Authorization", token)
                }.body<HttpResponse>()
            if (response.status.value in 200..299) {
                Response(isValid = true, data = response.body<List<CustomerResponse>>())
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
        return processCustomerResponse(resp)
    }

    override suspend fun getCustomersByCenterIdWithPaginationAndSort(
        token: String,
        centerId: Int,
        page: Int,
        limit: Int
    ): Resp<List<CustomerResp>> {
        val resp = try {
            val response =
                httpClient.get(urlString = "${NetworkConstants.SERVER}${CustomerConstants.CUSTOMERS}/$centerId/$page/$limit") {
                    contentType(ContentType.Application.Json)
                    header(key = "Authorization", token)
                }.body<HttpResponse>()
            if (response.status.value in 200..299) {
                Response(isValid = true, data = response.body<List<CustomerResponse>>())
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
        return processCustomerResponse(resp)
    }
}