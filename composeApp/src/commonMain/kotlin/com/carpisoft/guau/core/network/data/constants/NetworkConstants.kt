package com.carpisoft.guau.core.network.data.constants

class NetworkConstants {
    companion object{
        const val SERVER = "http://10.0.2.2:8080"
        //const val SERVER = "http://localhost:8080"
    }

    object Backendless{
        const val SERVER = "https://perkysmile-us.backendless.app/api/"
    }

    object BackendlessQueryWords{
        const val WHERE = "where"
        const val LOAD_RELATIONS = "loadRelations"
        const val PAGE_SIZE = "pageSize"
        const val OFF_SET = "offset"
    }

    object BackendlessHeaders{
        const val USER_TOKEN = "user-token"
    }
}