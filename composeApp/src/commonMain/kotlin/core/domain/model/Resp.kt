package core.domain.model

data class Resp<T>(
    val isValid:Boolean,
    val error:String?=null,
    val param: String?=null,
    val errorCode:Int?=null,
    val data:T?=null
)