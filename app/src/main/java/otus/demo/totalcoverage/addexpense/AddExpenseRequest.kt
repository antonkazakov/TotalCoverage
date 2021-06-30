package otus.demo.totalcoverage.addexpense

import otus.demo.totalcoverage.baseexpenses.Category

data class AddExpenseRequest(
    private val name: String,
    private val category: Category,
    private val amount: Long,
    private val comment: String?
)