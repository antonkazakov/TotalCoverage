package otus.demo.totalcoverage

import android.app.Application
import otus.demo.totalcoverage.di.AppComponent
import otus.demo.totalcoverage.di.DaggerAppComponent

open class ExpensesApp : Application() {

    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.create()
    }

    open fun getAppComponent(): AppComponent {
        return appComponent
    }

    protected open fun initAnalytics(){
        //some analytics initialization
    }
}