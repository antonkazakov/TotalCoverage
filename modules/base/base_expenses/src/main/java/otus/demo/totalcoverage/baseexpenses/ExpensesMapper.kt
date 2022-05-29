package otus.demo.totalcoverage.baseexpenses

import otus.demo.totalcoverage.coreapi.Expense
import otus.demo.totalcoverage.coreapi.Open
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@Open
class ExpensesMapper @Inject constructor() {

    fun map(expensesResponse: ExpenseResponse): Expense {
        return with(expensesResponse) {
            Expense(
                id = id,
                title = name,
                category = category,
                amount = amount,
                comment = comment,
                date = timeStamp.formatDate()
            )
        }
    }

    private fun Long.formatDate(): String {
        val simpleDateFormat = SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault())
        return simpleDateFormat.format(Date(this))
    }
}