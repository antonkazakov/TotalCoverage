package otus.demo.totalcoverage.addexpense

import io.reactivex.Single
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import otus.demo.totalcoverage.baseexpenses.Category
import otus.demo.totalcoverage.baseexpenses.ExpensesService
import otus.demo.totalcoverage.expenseslist.ExpensesMapper
import otus.demo.totalcoverage.testutils.ExpensesFactory

class AddExpensesInteractorTest {

    private val expensesService: ExpensesService = mock()
    private val expensesMapper: ExpensesMapper = mock()

    private val addExpensesInteractor = AddExpensesInteractor(expensesService, expensesMapper)

    @Test
    fun `should emit Expense and complete`() {
        val expected = ExpensesFactory.getExpense()
        whenever(expensesService.addExpense(any())).thenReturn(Single.just(ExpensesFactory.getExpenseResponse()))
        whenever(expensesMapper.map(any())).thenReturn(expected)

        addExpensesInteractor
            .addExpense("dummy_title", 100L, Category.BARS, "dummy_comment")
            .test()
            .assertValue(expected)
    }
}