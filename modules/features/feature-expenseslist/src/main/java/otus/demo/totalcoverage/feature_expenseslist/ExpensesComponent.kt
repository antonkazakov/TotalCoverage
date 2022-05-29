package otus.demo.totalcoverage.feature_expenseslist

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import otus.demo.totalcoverage.baseexpenses.ExpensesBaseModule
import otus.demo.totalcoverage.coreapi.AggregatingProvider
import otus.demo.totalcoverage.coreapi.FeatureScope
import javax.inject.Qualifier

@FeatureScope
@Component(
    modules = [ExpensesModule::class, ExpensesBaseModule::class, ExpensesNavModule::class],
    dependencies = [AggregatingProvider::class]
)
interface ExpensesComponent {

    companion object {

        fun create(
            aggregatingProvider: AggregatingProvider
        ): ExpensesComponent {
            return DaggerExpensesComponent.builder()
                .aggregatingProvider(aggregatingProvider)
                .build()
        }
    }

    fun inject(expensesFragment: ExpensesFragment)
}

@Module
interface ExpensesModule {

    companion object {

        @IO
        @Provides
        fun providesIoDispatcher(): CoroutineDispatcher {
            return Dispatchers.IO
        }
    }

    @Binds
    fun bindRepository(expensesRepositoryImpl: ExpensesRepositoryImpl): ExpensesRepository

    @Binds
    fun bindFactory(expensesViewModelFactory: ExpensesViewModelFactory): ViewModelProvider.Factory
}

@Qualifier
annotation class IO