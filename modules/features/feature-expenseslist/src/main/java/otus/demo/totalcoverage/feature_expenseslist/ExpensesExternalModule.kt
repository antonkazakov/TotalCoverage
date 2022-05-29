package otus.demo.totalcoverage.feature_expenseslist

import dagger.Binds
import dagger.Module
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap
import otus.demo.totalcoverage.expenseslistap.ExpensesListMediator

@Module
interface ExpensesExternalModule {

    @Binds
    @IntoMap
    @ClassKey(ExpensesListMediator::class)
    fun putExpensesMediator(expensesListMediator: ExpensesListMediatorImpl): Any
}