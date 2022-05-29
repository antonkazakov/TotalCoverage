package otus.demo.totalcoverage.addexpenses

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import otus.demo.totalcoverage.baseexpenses.ExpensesBaseModule
import otus.demo.totalcoverage.coreapi.AggregatingProvider
import otus.demo.totalcoverage.coreapi.FeatureScope

@FeatureScope
@Component(
    modules = [AddExpensesModule::class, ExpensesBaseModule::class],
    dependencies = [AggregatingProvider::class]
)
interface AddExpensesComponent {

    companion object {

        fun create(aggregatingProvider: AggregatingProvider): AddExpensesComponent {
            return DaggerAddExpensesComponent.builder().aggregatingProvider(aggregatingProvider).build()
        }
    }

    fun inject(expensesListFragment: AddExpenseFragment)
}

@Module
interface AddExpensesModule {

    companion object {

        @Provides
        fun providesScope(): CompositeDisposable {
            return CompositeDisposable()
        }
    }

    @Binds
    fun bindFactory(expensesViewModelFactory: AddExpenseViewModelModelFactory): ViewModelProvider.Factory
}