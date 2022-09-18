package app.country.ui.home

import android.util.Log
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.koin.core.annotation.Single

@Single
class HomeRepository {

    private val client = HttpClient(CIO) {
        defaultRequest {
            url("https://countries.trevorblades.com/")
        }

        install(ContentNegotiation){

            json(Json {
                prettyPrint = true
                isLenient = true
            })
        }

        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    Log.v("Logger Ktor =>", message)
                }
            }
            level = LogLevel.ALL
        }

    }

    suspend fun postCountries(){
        client.post {
            setBody("{\"query\":\"{  countries {    code    name    continent {      name    }  }}\"}")
        }
    }


}