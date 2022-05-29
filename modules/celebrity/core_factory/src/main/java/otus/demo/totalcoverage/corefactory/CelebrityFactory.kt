package otus.demo.totalcoverage.corefactory

import android.content.Context
import otus.demo.totalcoverage.coreapi.CoreProvider
import otus.demo.totalcoverage.coreimpl.CoreComponent

object CelebrityFactory {

    fun createCoreProvider(context: Context): CoreProvider {
        return CoreComponent.create(context)
    }
}