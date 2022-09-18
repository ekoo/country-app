package app.country.enum

import androidx.annotation.IdRes
import app.country.R

enum class Continent(val code: String,@IdRes val id: Int) {
    AFRICA("AF", R.id.africa),
    ANTARTICA("AN", R.id.antartica),
    ASIA("AS", R.id.asia),
    EUROPE("EU", R.id.europe),
    NORTH_AMERICA("NA", R.id.northAmerica),
    OCEANIA("OC", R.id.oceania),
    SOUTH_AMERICA("SA", R.id.southAmerica)
}