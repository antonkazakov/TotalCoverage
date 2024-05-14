package otus.demo.totalcoverage.di

import androidx.test.espresso.idling.CountingIdlingResource
import dagger.BindsOptionalOf
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import otus.demo.totalcoverage.baseexpenses.ExpensesService
import otus.demo.totalcoverage.baseexpenses.FakeExpensesServiceImpl
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

private const val URL = "http://localhost"

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

//    @Provides
//    @Singleton
//    fun provideExpensesNetworkService(retrofit: Retrofit): ExpensesService {
//        return retrofit.create(ExpensesService::class.java)
//    }

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

@Module
@InstallIn(SingletonComponent::class)
interface IdlingResourceModule {

    @BindsOptionalOf
    fun provideIdlingResource(): CountingIdlingResource
}