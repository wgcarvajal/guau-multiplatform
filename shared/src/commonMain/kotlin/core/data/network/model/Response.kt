package core.data.network.model

data class Response<T>(
    val isValid:Boolean,
    val error:String?=null,
    val errorCode:Int?=null,
    val data:T?=null
)