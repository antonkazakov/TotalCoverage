package otus.demo.totalcoverage.addexpenses

import androidx.fragment.app.FragmentManager
import otus.demo.totalcoverage.featureexpensesapi.AddExpensesMediator
import javax.inject.Inject

class AddExpensesMediatorImpl @Inject constructor() : AddExpensesMediator {

    override fun openAddExpensesScreen(
        fragmentManager: FragmentManager,
        container: Int
    ) {
        fragmentManager.beginTransaction()
            .replace(container, AddExpenseFragment())
            .addToBackStack("BACKSTACK")
            .commit()
    }
}