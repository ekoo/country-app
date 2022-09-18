package app.country

import android.app.Application
import androidx.room.Room
import app.country.db.CountryDB
import org.koin.android.ext.koin.androidContext
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.ksp.generated.module

class CountryApp: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@CountryApp)
            modules(AndroidAppModule().module, module {
                single {
                    Room.databaseBuilder(androidContext(), CountryDB::class.java, "app.db")
                        .fallbackToDestructiveMigration()
                        .build()
                }

                single { get<CountryDB>().favoriteCountryDao() }
            })
        }
    }
}

@Module
@ComponentScan("app.country")
class AndroidAppModule