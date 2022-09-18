package app.country.util

import androidx.appcompat.app.AppCompatDelegate


fun changeTheme(which: Int) {
    val mode = when(which){
        0 -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
        1 -> AppCompatDelegate.MODE_NIGHT_NO
        else -> AppCompatDelegate.MODE_NIGHT_YES
    }
    AppCompatDelegate.setDefaultNightMode(mode)
}