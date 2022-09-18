package app.country.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity
data class Country(
    @PrimaryKey
    val code: String,
    val name: String,
    @Embedded
    val continent: Continent
)