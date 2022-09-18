package app.country.db

import androidx.lifecycle.LiveData
import androidx.room.*
import app.country.model.Country

@Dao
interface CountryDao {

    @Query("SELECT * FROM country")
    fun favoriteCountries(): LiveData<List<Country>>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun addFavorite(country: Country): Long

    @Delete
    suspend fun deleteFavorite(country: Country): Int
}