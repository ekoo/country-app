package app.country

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.preference.PreferenceManager
import app.country.databinding.ActivityMainBinding
import app.country.util.changeTheme

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        binding = ActivityMainBinding.inflate(layoutInflater)
        val preference = PreferenceManager.getDefaultSharedPreferences(this)
        changeTheme(preference.getInt("theme", 0))
        setContentView(binding.root)
        binding.navView.setupWithNavController(
            findNavController(R.id.nav_host_fragment_activity_main)
        )

    }
}