package otus.demo.totalcoverage.addexpense

import io.reactivex.Single
import otus.demo.totalcoverage.Open
import otus.demo.totalcoverage.baseexpenses.ExpensesService
import otus.demo.totalcoverage.baseexpenses.Category
import otus.demo.totalcoverage.baseexpenses.Expense
import otus.demo.totalcoverage.expenseslist.ExpensesMapper
import otus.demo.totalcoverage.utils.NeedsTesting
import javax.inject.Inject

@NeedsTesting
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
    ): AddExpenseRequest {
        return AddExpenseRequest(
            name = title,
            amount = amount,
            category = category,
            comment = comment
        )
    }
}