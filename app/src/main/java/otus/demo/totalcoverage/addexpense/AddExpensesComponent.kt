package otus.demo.totalcoverage.addexpense

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import io.reactivex.disposables.CompositeDisposable

@Module
@InstallIn(ViewModelComponent::class)
object AddExpensesModule {

    @Provides
    fun provideDisposable(): CompositeDisposable {
        return CompositeDisposable()
    }
}