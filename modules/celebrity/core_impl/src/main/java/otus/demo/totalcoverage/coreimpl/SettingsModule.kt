package otus.demo.totalcoverage.coreimpl

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object SettingsModule {

    private const val CORE_PREFS = "CORE_PREFS"

    @Provides
    @Singleton
    fun providePreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(CORE_PREFS, Context.MODE_PRIVATE)
    }
}