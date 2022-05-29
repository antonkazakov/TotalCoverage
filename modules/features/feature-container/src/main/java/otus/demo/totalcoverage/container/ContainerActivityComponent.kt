package otus.demo.totalcoverage.container

import dagger.Component
import otus.demo.totalcoverage.coreapi.AggregatingProvider

@Component(dependencies = [AggregatingProvider::class], modules = [ExpensesNavModule::class])
interface ContainerActivityComponent {

    fun inject(containerActivity: ContainerActivity)
}

