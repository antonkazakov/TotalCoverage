package otus.demo.totalcoverage.coreimpl

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import otus.demo.totalcoverage.coreapi.CoreProvider
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, SettingsModule::class])
interface CoreComponent : CoreProvider {

    companion object {

        fun create(context: Context): CoreProvider {
            return DaggerCoreComponent.factory().create(context)
        }
    }

    @Component.Factory
    interface Factory {

        fun create(@BindsInstance context: Context): CoreComponent
    }
}