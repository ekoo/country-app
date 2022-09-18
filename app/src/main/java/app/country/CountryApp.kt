package app.country

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.context.startKoin
import org.koin.ksp.generated.module

class CountryApp: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@CountryApp)
            modules(AndroidAppModule().module)
        }
    }
}

@Module
@ComponentScan("app.country")
class AndroidAppModule