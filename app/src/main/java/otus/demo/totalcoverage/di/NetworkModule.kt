package otus.demo.totalcoverage.di

import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.Reusable
import otus.demo.totalcoverage.baseexpenses.ExpensesService
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
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

    @Provides
    @Singleton
    fun provideExpensesService(retrofit: Retrofit): ExpensesService {
        return retrofit.create(ExpensesService::class.java)
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