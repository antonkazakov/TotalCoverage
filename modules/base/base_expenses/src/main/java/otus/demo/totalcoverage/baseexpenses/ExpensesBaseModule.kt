package otus.demo.totalcoverage.baseexpenses

import dagger.Module
import dagger.Provides

@Module
class ExpensesBaseModule {

//    @Provides
//    fun provideService(retrofit: Retrofit): ExpensesService {
//        return retrofit.create(ExpensesService::class.java)
//    }

    @Provides
    fun getService(): ExpensesService {
        return FakeExpensesServiceImpl()
    }
}