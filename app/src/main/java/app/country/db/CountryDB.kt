package app.country.db

import androidx.room.Database
import androidx.room.RoomDatabase
import app.country.model.Country

@Database(
    entities = [Country::class],
    version = 1,
    exportSchema = false
)
abstract class CountryDB: RoomDatabase() {
    abstract fun favoriteCountryDao(): CountryDao
}