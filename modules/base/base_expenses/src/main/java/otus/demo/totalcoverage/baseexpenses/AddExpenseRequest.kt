package otus.demo.totalcoverage.baseexpenses

import otus.demo.totalcoverage.coreapi.Category

data class AddExpenseRequest(
    val name: String,
    val category: Category,
    val amount: Long,
    val comment: String?
)