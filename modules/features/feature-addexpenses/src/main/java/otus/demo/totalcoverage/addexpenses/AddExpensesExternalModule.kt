package otus.demo.totalcoverage.addexpenses

import dagger.Binds
import dagger.Module
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap
import otus.demo.totalcoverage.featureexpensesapi.AddExpensesMediator

@Module
interface AddExpensesExternalModule {

    @Binds
    @IntoMap
    @ClassKey(AddExpensesMediator::class)
    fun putAddExpensesMediator(addExpensesMediatorImpl: AddExpensesMediatorImpl): Any
}