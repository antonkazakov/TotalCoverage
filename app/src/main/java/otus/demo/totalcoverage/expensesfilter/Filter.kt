package otus.demo.totalcoverage.expensesfilter

import otus.demo.totalcoverage.baseexpenses.Category
import java.io.Serializable

data class Filter(
    val categories: List<Category> = Category.values().toList(),
    val amountRange: LongRange = Long.MIN_VALUE..Long.MAX_VALUE,
    val sort: Sort = Sort.DESC_DATE
) : Serializable

enum class Sort {
    ASC, DESC, DESC_DATE, ASC_DATE
}