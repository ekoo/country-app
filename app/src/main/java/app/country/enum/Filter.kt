package app.country.enum

import androidx.annotation.IdRes
import app.country.R

enum class Filter(@IdRes val id: Int) {
    COUNTRY(R.id.byCountyName),
    CONTINENT(R.id.byContinentName)
}