package otus.demo.totalcoverage.container

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import otus.demo.totalcoverage.R
import otus.demo.totalcoverage.coreapi.ProvidersHolder
import otus.demo.totalcoverage.expenseslistap.ExpensesListMediator
import javax.inject.Inject

class ContainerActivity : AppCompatActivity() {

    @Inject
    lateinit var expensesListMediator: ExpensesListMediator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_container)
        DaggerContainerActivityComponent.builder()
            .aggregatingProvider((application as ProvidersHolder).getAggregatingProvider()).build()
            .inject(this)
        expensesListMediator.openExpensesListContainerScreen(
            supportFragmentManager,
            R.id.host_container
        )
    }
}