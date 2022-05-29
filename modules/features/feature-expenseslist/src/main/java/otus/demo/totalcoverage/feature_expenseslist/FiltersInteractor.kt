package otus.demo.totalcoverage.feature_expenseslist

import otus.demo.totalcoverage.baseexpenses.ExpenseResponse
import otus.demo.totalcoverage.baseexpenses.ExpensesService
import otus.demo.totalcoverage.coreapi.Filter
import otus.demo.totalcoverage.coreapi.Sort
import javax.inject.Inject

@otus.demo.totalcoverage.coreapi.Open
class FiltersInteractor @Inject constructor(
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