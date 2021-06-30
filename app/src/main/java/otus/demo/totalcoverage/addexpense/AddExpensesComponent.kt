package otus.demo.totalcoverage.addexpense

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import otus.demo.totalcoverage.di.AppComponent
import otus.demo.totalcoverage.di.FeatureScope

@FeatureScope
@Component(
    modules = [AddExpensesModule::class],
    dependencies = [AppComponent::class]
)
interface AddExpensesComponent {

    companion object {
        fun getAddExpensesComponent(appComponent: AppComponent): AddExpensesComponent {
            return DaggerAddExpensesComponent.builder().appComponent(appComponent).build()
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