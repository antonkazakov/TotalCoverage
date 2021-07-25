package otus.demo.totalcoverage.addexpense

import otus.demo.totalcoverage.baseexpenses.Category

data class AddExpenseRequest(
    val name: String,
    val category: Category,
    val amount: Long,
    val comment: String?
)