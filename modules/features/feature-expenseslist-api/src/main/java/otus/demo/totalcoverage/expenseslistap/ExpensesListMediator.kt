package otus.demo.totalcoverage.expenseslistap

import androidx.annotation.IdRes
import androidx.fragment.app.FragmentManager

interface ExpensesListMediator {

    fun openExpensesListContainerScreen(fragmentManager: FragmentManager, @IdRes container: Int)
}