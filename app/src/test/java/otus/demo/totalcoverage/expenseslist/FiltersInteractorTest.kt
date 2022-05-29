package otus.demo.totalcoverage.expenseslist

import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import otus.demo.totalcoverage.baseexpenses.Category
import otus.demo.totalcoverage.expensesfilter.Filter
import otus.demo.totalcoverage.expensesfilter.Sort

class FiltersInteractorTest {

    private val expensesService: otus.demo.totalcoverage.baseexpenses.ExpensesService = mock()
    private val filtersInteractor =
        otus.demo.totalcoverage.feature_expenseslist.FiltersInteractor(expensesService)

    private val firstResponse = otus.demo.totalcoverage.baseexpenses.ExpenseResponse(
        1, "first", Category.BARS, null, 1000L, 1624345281000L
    )
    private val secondResponse = otus.demo.totalcoverage.baseexpenses.ExpenseResponse(
        2, "second", Category.BARS, null, 3000L, 1624345261000L
    )
    private val thirdResponse = otus.demo.totalcoverage.baseexpenses.ExpenseResponse(
        3, "third", Category.TRAVEL, null, 2000L, 1624345221000L
    )

    @Before
    fun before() {
        runTest {
            whenever(expensesService.getExpenses()).thenReturn(
                mutableListOf(
                    firstResponse,
                    secondResponse,
                    thirdResponse
                )
            )
        }
    }

    @Test
    fun `should sort descending by amount`() {
        runTest {
            val filter = Filter(sort = Sort.DESC)

            val expected = listOf(
                secondResponse,
                thirdResponse,
                firstResponse
            )

            val actual = filtersInteractor.getFilteredExpenses(filter)

            assertEquals(expected, actual)
        }
    }

    @Test
    fun `should sort by asc amount and filter bars`() {
        runTest {
            val filter = Filter(categories = listOf(Category.BARS), sort = Sort.ASC)

            val expected = listOf(
                firstResponse, secondResponse
            )

            val actual = filtersInteractor.getFilteredExpenses(filter)

            assertEquals(expected, actual)
        }
    }
}