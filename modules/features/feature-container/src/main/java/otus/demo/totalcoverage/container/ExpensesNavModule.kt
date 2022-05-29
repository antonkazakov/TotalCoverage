package otus.demo.totalcoverage.container

import dagger.Module
import dagger.Provides
import otus.demo.totalcoverage.expenseslistap.ExpensesListMediator
import javax.inject.Provider

@Module
object ExpensesNavModule {

    @Provides
    fun provideAddExpensesMediator(map: Map<Class<*>, @JvmSuppressWildcards Provider<Any>>): ExpensesListMediator {
        return map[ExpensesListMediator::class.java]?.get() as ExpensesListMediator
    }
}