package app.country.model

import androidx.room.ColumnInfo
import kotlinx.serialization.Serializable

@Serializable
data class Continent(
    @ColumnInfo(name = "continent")
    val name: String
)