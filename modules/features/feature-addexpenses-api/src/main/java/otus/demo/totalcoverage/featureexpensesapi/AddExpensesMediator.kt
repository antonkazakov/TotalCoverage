package otus.demo.totalcoverage.featureexpensesapi

import androidx.annotation.IdRes
import androidx.fragment.app.FragmentManager

interface AddExpensesMediator {

    fun openAddExpensesScreen(fragmentManager: FragmentManager, @IdRes container: Int)
}