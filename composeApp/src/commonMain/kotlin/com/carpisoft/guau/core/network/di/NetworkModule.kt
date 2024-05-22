package com.carpisoft.guau.core.network.di

import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.module.Module
import org.koin.dsl.module

val networkModule: Module = module {
    single(createdAtStart = true) {
        HttpClient {
            install(ContentNegotiation) {
                json(Json{
                    ignoreUnknownKeys = true
                })
            }
            install(HttpTimeout) {
                requestTimeoutMillis = 30000
            }
        }
    }
}