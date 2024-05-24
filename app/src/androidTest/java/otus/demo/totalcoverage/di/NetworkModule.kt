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
import javax.inject.Singleton

private const val URL = "http://localhost"

@Singleton
@Component(modules = [TestNetworkModule::class, TestIdlingResourceModule::class])
interface TestAppComponent : AppComponent {

    fun inject(userFlowTest: UserFlowSimple)
}

@Module
object TestNetworkModule {

    @Provides
    @Singleton
    fun provideExpensesNetworkService(): ExpensesService {
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
interface TestIdlingResourceModule {

    companion object {

        @Provides
        @Singleton
        fun provideIdlingResource(): CountingIdlingResource {
            return CountingIdlingResource("test_resource")
        }
    }

    @BindsOptionalOf
    fun provideIdlingResource(): CountingIdlingResource
}