package initialsetup.data.network.repository

import core.data.network.constants.NetworkConstants
import core.data.network.model.Response
import core.data.network.model.error.ResponseError
import core.domain.model.Resp
import initialsetup.data.network.constants.InitialSetupConstants
import initialsetup.data.network.model.EmployeeResponse
import initialsetup.data.network.model.EmployeesRequest
import initialsetup.domain.model.EmployeeResp
import initialsetup.domain.model.EmployeesReq
import initialsetup.domain.port.InitialSetupPort
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType


class InitialSetupRepository(
    private val httpClient: HttpClient
) : InitialSetupPort,
    InitialSetupRepositoryHelper() {
    override suspend fun getEmployees(
        token: String,
        employeesReq: EmployeesReq
    ): Resp<List<EmployeeResp>> {

        val resp = try {
            val response =
                httpClient.post(urlString = "${NetworkConstants.SERVER}${InitialSetupConstants.EMPLOYEES}") {
                    contentType(ContentType.Application.Json)
                    header(key = "Authorization",token)
                    setBody(
                        EmployeesRequest(
                            email = employeesReq.email,
                            rol = employeesReq.rol
                        )
                    )
                }.body<HttpResponse>()
            if (response.status.value in 200..299) {
                Response(isValid = true, data = response.body<List<EmployeeResponse>>())
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