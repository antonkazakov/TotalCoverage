package otus.demo.totalcoverage.expenseslist

import otus.demo.totalcoverage.baseexpenses.ExpensesService
import otus.demo.totalcoverage.baseexpenses.ExpenseResponse
import otus.demo.totalcoverage.expensesfilter.Filter
import otus.demo.totalcoverage.expensesfilter.Sort
import otus.demo.totalcoverage.utils.NeedsTesting
import javax.inject.Inject

@NeedsTesting
open class FiltersInteractor @Inject constructor(
    private val expensesService: ExpensesService
) {

    suspend fun getFilteredExpenses(filter: Filter): List<ExpenseResponse> {
        return expensesService.getExpenses()
            .filter { it.category in filter.categories }
            .filter { it.amount in filter.amountRange }
            .sortedWith(comparatorFactory(filter.sort))
    }

    private fun comparatorFactory(sort: Sort): Comparator<ExpenseResponse> {
        return when (sort) {
            Sort.ASC -> compareBy { it.amount }
            Sort.DESC -> compareByDescending { it.amount }
            Sort.ASC_DATE -> compareBy { it.timeStamp }
            Sort.DESC_DATE -> compareByDescending { it.timeStamp }
        }
    }
}