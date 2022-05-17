package otus.demo.totalcoverage.baseexpenses

import java.io.Serializable

data class Expense(
    val id: Long,
    val title: String,
    val category: Category,
    val comment: String? = null,
    val amount: Long,
    val date: String
) : Serializable

enum class Category {
    FOOD, HEALTH, TRANSPORT, BARS, TRAVEL
}