package otus.demo.totalcoverage

import dagger.Component
import otus.demo.totalcoverage.addexpenses.AddExpensesExternalModule
import otus.demo.totalcoverage.coreapi.AggregatingProvider
import otus.demo.totalcoverage.coreapi.CoreProvider
import otus.demo.totalcoverage.feature_expenseslist.ExpensesExternalModule
import otus.demo.totalcoverage.featurefilters.FiltersExternalModule
import javax.inject.Singleton

@Component(
    modules = [AddExpensesExternalModule::class, FiltersExternalModule::class, ExpensesExternalModule::class],
    dependencies = [CoreProvider::class]
)
@Singleton
interface AggregatingComponent : AggregatingProvider {

    @Component.Factory
    interface Factory {

        fun create(coreProvider: CoreProvider): AggregatingComponent
    }
}