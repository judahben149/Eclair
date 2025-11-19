package com.judahben149.eclair.data.remote.api

import com.judahben149.eclair.data.remote.dto.Concept
import com.judahben149.eclair.data.remote.dto.ConceptListItem
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.http.URLProtocol
import io.ktor.http.path
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

interface ConceptApiService {
    suspend fun getAllConcepts(): List<ConceptListItem>
    suspend fun getConceptById(id: Int): Concept
}

class ConceptApiServiceImpl(private val client: HttpClient) : ConceptApiService {
    override suspend fun getAllConcepts(): List<ConceptListItem> {
        return client.get {
            url {
                path("concepts")
            }
        }.body()
    }

    override suspend fun getConceptById(id: Int): Concept {
        return client.get {
            url {
                path("concepts/$id")
            }
        }.body()
    }
}

fun createHttpClient(): HttpClient {
    return HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
                prettyPrint = true
            })
        }

        defaultRequest {
            url {
                protocol = URLProtocol.HTTPS
                host = "eclair-admin-server-production-b1b2.up.railway.app"
                path("api/v1/")
            }
            header("Content-Type", "application/json")
        }
    }
}
