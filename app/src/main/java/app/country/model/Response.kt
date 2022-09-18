package app.country.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Response(
    @SerialName("data")
    val `data`: Data
) {
    @Serializable
    data class Data(
        val countries: List<Country>
    )
}