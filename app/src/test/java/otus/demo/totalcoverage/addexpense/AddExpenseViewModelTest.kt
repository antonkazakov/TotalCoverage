package otus.demo.totalcoverage.addexpense

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.reactivex.Single
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.anyOrNull
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import otus.demo.totalcoverage.baseexpenses.Category
import otus.demo.totalcoverage.testutils.ExpensesFactory
import otus.demo.totalcoverage.testutils.RxRule
import java.lang.RuntimeException

class AddExpenseViewModelTest {

    @get:Rule
    val rxRule = RxRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val addExpensesInteractor: AddExpensesInteractor = mock()

    private val addExpenseViewModel =
        AddExpenseViewModel(addExpensesInteractor)

    @Test
    fun `should send Success event`() {
        whenever(addExpensesInteractor.addExpense(any(), any(), any(), anyOrNull()))
            .thenReturn(Single.just(ExpensesFactory.getExpense()))

        addExpenseViewModel
            .addExpense("dummy_title", "100", Category.BARS, "dummy_comment")

        val actual = addExpenseViewModel.liveData.value
        assertEquals(Success(ExpensesFactory.getExpense()), actual)
    }

    @Test
    fun `should send Error event when failure was emited`() {
        val expectedException = RuntimeException("failure")
        whenever(addExpensesInteractor.addExpense(any(), any(), any(), anyOrNull()))
            .thenReturn(Single.error(expectedException))
        addExpenseViewModel
            .addExpense("dummy_title", "100", Category.BARS, "dummy_comment")

        val actual = addExpenseViewModel.liveData.value
        assertEquals(Error(expectedException), actual)
    }
}