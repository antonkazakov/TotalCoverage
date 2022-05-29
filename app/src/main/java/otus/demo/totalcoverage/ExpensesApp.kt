package otus.demo.totalcoverage

import android.app.Application
import otus.demo.totalcoverage.coreapi.AggregatingProvider
import otus.demo.totalcoverage.coreapi.ProvidersHolder
import otus.demo.totalcoverage.corefactory.CelebrityFactory

open class ExpensesApp : Application(), ProvidersHolder {

    companion object {

        private var aggregatingProvider: AggregatingProvider? = null
    }

    override fun onCreate() {
        super.onCreate()
        getAggregatingProvider()
    }

    override fun getAggregatingProvider(): AggregatingProvider {
        return aggregatingProvider ?: DaggerAggregatingComponent.factory()
            .create(CelebrityFactory.createCoreProvider(this)).also { aggregatingProvider = it }
    }
}