package otus.demo.totalcoverage.expenseslist

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier

@Module
@InstallIn(ViewModelComponent::class)
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
}

@Qualifier
annotation class IO