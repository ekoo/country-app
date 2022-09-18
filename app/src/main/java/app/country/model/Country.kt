package app.country.model

import kotlinx.serialization.Serializable

@Serializable
data class Country(
    val code: String,
    val name: String,
    val continent: Continent
)