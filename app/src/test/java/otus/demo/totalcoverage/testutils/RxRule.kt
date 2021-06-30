package otus.demo.totalcoverage.testutils

import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.rules.ExternalResource

class RxRule : ExternalResource() {

    override fun before() {
        super.before()
        replaceSchedulers()
    }

    private fun replaceSchedulers() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setInitNewThreadSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setSingleSchedulerHandler { Schedulers.trampoline() }
    }

    override fun after() {
        super.after()
        RxJavaPlugins.reset()
        RxAndroidPlugins.reset()
    }
}