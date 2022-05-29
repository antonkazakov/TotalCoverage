package otus.demo.totalcoverage.feature_expenseslist

import dagger.Module
import dagger.Provides
import otus.demo.totalcoverage.featureexpensesapi.AddExpensesMediator
import otus.demo.totalcoverage.featurefiltersapi.FiltersMediator
import javax.inject.Provider

@Module
object ExpensesNavModule {

    @Provides
    fun provideAddExpensesMediator(map: Map<Class<*>, @JvmSuppressWildcards Provider<Any>>): AddExpensesMediator {
        return map[AddExpensesMediator::class.java]?.get() as AddExpensesMediator
    }

    @Provides
    fun provideFiltersMediator(map: Map<Class<*>, @JvmSuppressWildcards Provider<Any>>): FiltersMediator {
        return map[FiltersMediator::class.java]?.get() as FiltersMediator
    }
}