package otus.demo.totalcoverage.di

import otus.demo.totalcoverage.ExpensesApp

class TestApp : ExpensesApp() {

    companion object {

        var INSTANCE: AppComponent? = null
    }

    override fun getAppComponent(): AppComponent {
        return INSTANCE ?: DaggerTestAppComponent.create().also { INSTANCE = it }
    }

    override fun initAnalytics() {
    }
}