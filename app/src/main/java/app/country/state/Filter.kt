package app.country.state

import androidx.annotation.IdRes
import app.country.R

enum class Filter(@IdRes val id: Int) {
    COUNTRY(R.id.byCountyName),
    CONTINENT(R.id.byContinentName)
}