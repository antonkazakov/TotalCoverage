package otus.demo.totalcoverage.feature_expenseslist

import androidx.fragment.app.FragmentManager
import otus.demo.totalcoverage.expenseslistap.ExpensesListMediator
import javax.inject.Inject

class ExpensesListMediatorImpl @Inject constructor() : ExpensesListMediator {

    override fun openExpensesListContainerScreen(fragmentManager: FragmentManager, container: Int) {
        fragmentManager
            .beginTransaction()
            .add(container, ExpensesContainerFragment()).commit()
    }
}