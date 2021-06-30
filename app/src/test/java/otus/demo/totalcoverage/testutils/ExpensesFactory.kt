package otus.demo.totalcoverage.testutils

import otus.demo.totalcoverage.baseexpenses.Category
import otus.demo.totalcoverage.baseexpenses.Expense
import otus.demo.totalcoverage.baseexpenses.ExpenseResponse

object ExpensesFactory {

    fun getExpenses() = listOf(getExpense(), getExpense())

    fun getExpense(): Expense {
        return Expense(
            id = 100,
            title = "dummy_name",
            category = Category.FOOD,
            comment = "dummy_comment",
            amount = 3000L,
            date = "22-06-2021 10:01"
        )
    }

    fun getExpenseResponse(): ExpenseResponse {
        return ExpenseResponse(
            1,
            "Some food",
            Category.FOOD,
            "Some grocery shop",
            1200L,
            1624345281000L
        )
    }

    fun getExpenseResponses(): List<ExpenseResponse> {
        return listOf(
            ExpenseResponse(
                1,
                "Some food",
                Category.FOOD,
                "Some grocery shop",
                1200L,
                1624345281000L
            ),
            ExpenseResponse(1, "Some food", Category.BARS, "Some bar", 3000L, 1624345281000L),
        )
    }
}