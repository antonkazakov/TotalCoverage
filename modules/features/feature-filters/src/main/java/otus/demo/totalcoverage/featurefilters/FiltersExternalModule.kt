package otus.demo.totalcoverage.featurefilters

import dagger.Binds
import dagger.Module
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap
import otus.demo.totalcoverage.featurefiltersapi.FiltersMediator

@Module
interface FiltersExternalModule {

    @Binds
    @IntoMap
    @ClassKey(FiltersMediator::class)
    fun putAddExpensesMediator(filtersMediatorImpl: FiltersMediatorImpl): Any
}