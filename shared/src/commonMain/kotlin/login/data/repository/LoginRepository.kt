package login.data.repository


import core.data.network.constants.NetworkConstants
import core.data.network.model.Response
import core.domain.model.Resp
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import login.data.network.constants.LoginConstants
import login.data.network.model.LoginRequest
import login.data.network.model.LoginResponse
import login.domain.model.LoginReq
import login.domain.model.LoginResp
import login.domain.port.LoginPort

class LoginRepository(
    private val httpClient: HttpClient
) : LoginPort, LoginRepositoryHelper() {
    override suspend fun doLogin(loginReq: LoginReq): Resp<LoginResp> {
        println("entro do login")
        val resp = try {
            val response =
                httpClient.post(urlString = "${NetworkConstants.SERVER}${LoginConstants.LOGIN_PATH}") {
                    contentType(ContentType.Application.Json)
                    setBody(LoginRequest(email = loginReq.email, password = loginReq.password))
                }.body<HttpResponse>()
            if(response.status.value in 200..299){
                Response(isValid = true, data = response.body<LoginResponse>())
            }
            else{
                val errorBody = response.body<String>()
                println("message $errorBody")
                Response(isValid = false, error = errorBody)
            }

        } catch (e: Exception) {
            println("message= $e")
            Response(isValid = false, error = e.message?:"")
        }
        return processResponse(response = resp)
    }
}