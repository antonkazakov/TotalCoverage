package otus.demo.totalcoverage.di

import androidx.test.espresso.idling.CountingIdlingResource
import dagger.BindsOptionalOf
import dagger.Component
import dagger.Module
import dagger.Provides
import otus.demo.totalcoverage.baseexpenses.ExpensesService
import otus.demo.totalcoverage.baseexpenses.FakeExpensesServiceImpl
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Optional
import javax.inject.Singleton

private const val URL = "http://localhost"

@Singleton
@Component(modules = [NetworkModule::class])
interface AppComponent {

    fun provideExpensesService(): ExpensesService

    fun provideIdlingResource():Optional<CountingIdlingResource>
}

@Module
interface NetworkModule {

//    @Provides
//    @Singleton
//    fun provideExpensesNetworkService(retrofit: Retrofit): ExpensesService {
//        return retrofit.create(ExpensesService::class.java)
//    }

    companion object {
        @Provides
        @Singleton
        fun provideFakeExpensesNetworkService(): ExpensesService {
            return FakeExpensesServiceImpl()
        }

        @Provides
        @Singleton
        fun provideRetrofitClient(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }

    @BindsOptionalOf
    fun provideIdlingResource(): CountingIdlingResource
}