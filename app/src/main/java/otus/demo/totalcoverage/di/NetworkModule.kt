package otus.demo.totalcoverage.di

import dagger.*
import otus.demo.totalcoverage.baseexpenses.ExpensesService
import otus.demo.totalcoverage.baseexpenses.FakeExpensesServiceImpl
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Qualifier
import javax.inject.Scope
import javax.inject.Singleton

private const val URL = "http://localhost"

@Singleton
@Component(modules = [NetworkModule::class])
interface AppComponent {

    fun provideExpensesService(): ExpensesService
}

@Module
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