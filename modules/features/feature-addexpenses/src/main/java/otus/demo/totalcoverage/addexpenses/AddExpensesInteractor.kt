package otus.demo.totalcoverage.addexpenses

import io.reactivex.Single
import otus.demo.totalcoverage.baseexpenses.ExpensesMapper
import otus.demo.totalcoverage.baseexpenses.ExpensesService
import otus.demo.totalcoverage.coreapi.Category
import otus.demo.totalcoverage.coreapi.Expense
import otus.demo.totalcoverage.coreapi.Open
import javax.inject.Inject

@Open
class AddExpensesInteractor @Inject constructor(
    private val expensesService: ExpensesService,
    private val expensesMapper: ExpensesMapper
) {

    fun addExpense(
        title: String,
        amount: Long,
        category: Category,
        comment: String? = null
    ): Single<Expense> {
        return expensesService
            .addExpense(assembleRequest(title, amount, category, comment))
            .map { expensesMapper.map(it) }
    }

    private fun assembleRequest(
        title: String,
        amount: Long,
        category: Category,
        comment: String? = null
    ): otus.demo.totalcoverage.baseexpenses.AddExpenseRequest {
        return otus.demo.totalcoverage.baseexpenses.AddExpenseRequest(
            name = title,
            amount = amount,
            category = category,
            comment = comment
        )
    }
}