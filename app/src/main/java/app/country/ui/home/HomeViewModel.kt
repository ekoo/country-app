package app.country.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.country.db.CountryDao
import app.country.state.Continent
import app.country.state.Filter
import app.country.model.Country
import app.country.model.Response
import app.country.state.Status
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class HomeViewModel(
    private val db: CountryDao
) : ViewModel() {

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

    private val _status = MutableLiveData<Status>()
    val status: LiveData<Status> get() = _status
    var errorMessage: String? = null

    private val _countries = MutableLiveData<List<Country>>()
    val countries: LiveData<List<Country>> get() = _countries
    val favoriteCountries get() = db.favoriteCountries()

    var filter = Filter.COUNTRY
    val selectedContinent = mutableListOf(
        Continent.AFRICA,
        Continent.ANTARTICA,
        Continent.ASIA,
        Continent.EUROPE,
        Continent.NORTH_AMERICA,
        Continent.OCEANIA,
        Continent.SOUTH_AMERICA
    )

    fun postCountry() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _status.postValue(Status.LOADING)
                delay(100)
                val response = client.post() {
                    contentType(ContentType.Application.Json)
                    setBody(requestBody())
                }

                if (!response.status.isSuccess()) {
                    errorMessage = response.status.description
                    _status.postValue(Status.ERROR)
                    return@launch
                }

                var countries = response.body<Response>().data.countries

                if (filter == Filter.CONTINENT){
                    countries = countries.sortedBy { it.continent.name }
                }

                _countries.postValue(countries)
                _status.postValue(Status.SUCCESS)
                errorMessage = null
            } catch (e: Exception) {
                errorMessage = e.message
                _status.postValue(Status.ERROR)
            }
        }
    }

    private fun requestBody(): String {
        if (selectedContinent.size < 7) {
            val continents = Json.encodeToString(selectedContinent.map { it.code })
            val query = "{ countries( filter: { continent: { in: $continents } } ) { code name continent { name } } }"
            return "{\"query\":${query.escapeIfNeeded()}}"
        }

        return "{\"query\":\"{ countries { code name continent { name } } }\"}"
    }

    fun saveCountry(country: Country, status: (String) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            var message = "Success added ${country.name} to favorite"
            try {
                if (db.addFavorite(country) < 1) message = "Already added to favorite"
            } catch (e: Exception) {
                message = e.message ?: ""
            }

            withContext(Dispatchers.Main) { status(message) }
        }
    }

    fun deleteCountry(country: Country, status: (String) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            var message = "${country.name} removed from favorite"
            try {
                if (db.deleteFavorite(country) < 1) message = "Already removed"
            } catch (e: Exception) {
                message = e.message ?: ""
            }

            withContext(Dispatchers.Main) { status(message) }
        }
    }

    init {
        postCountry()
    }
}