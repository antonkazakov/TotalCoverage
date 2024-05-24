package otus.demo.totalcoverage.baseexpenses

import io.reactivex.Single
import otus.demo.totalcoverage.addexpense.AddExpenseRequest
import java.util.concurrent.atomic.AtomicLong
import javax.inject.Inject

class FakeExpensesServiceImpl
@Inject constructor() : ExpensesService {

    private val expenses = mutableListOf<ExpenseResponse>()
    private val counter = AtomicLong(0)

    override suspend fun getExpenses(): List<ExpenseResponse> {
        Thread.sleep(2000)
        return expenses
    }

    override fun addExpense(addExpenseRequest: AddExpenseRequest): Single<ExpenseResponse> {
        val response = map(addExpenseRequest)
        val wasAdded = expenses.add(response)
        return if (wasAdded) Single.just(response) else Single.error(RuntimeException("Something went wrong"))
    }

    private fun map(addExpenseRequest: AddExpenseRequest): ExpenseResponse {
        return ExpenseResponse(
            id = counter.getAndIncrement(),
            name = addExpenseRequest.name,
            category = addExpenseRequest.category,
            comment = addExpenseRequest.comment,
            amount = addExpenseRequest.amount,
            timeStamp = System.currentTimeMillis()
        )
    }
}