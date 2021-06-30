package otus.demo.totalcoverage.baseexpenses

data class ExpenseResponse(
    val id: Long,
    val name: String,
    val category: Category,
    val comment: String?,
    val amount: Long,
    val timeStamp: Long
)