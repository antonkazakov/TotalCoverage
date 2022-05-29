package otus.demo.totalcoverage.baseexpenses

import otus.demo.totalcoverage.coreapi.Category

data class ExpenseResponse(
    val id: Long,
    val name: String,
    val category: Category,
    val comment: String?,
    val amount: Long,
    val timeStamp: Long
)