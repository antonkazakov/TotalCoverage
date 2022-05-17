package otus.demo.totalcoverage.expenseslist

import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.*
import otus.demo.totalcoverage.baseexpenses.ExpensesService
import otus.demo.totalcoverage.testutils.ExpensesFactory

class ExpensesRepositoryImplTest {

    private val expensesService: ExpensesService = mock()
    private val expensesMapper: ExpensesMapper = mock()

    private val repository = ExpensesRepositoryImpl(expensesService, expensesMapper)

    @Test
    fun `should return mapped expenses`() {
        runTest {
            whenever(expensesService.getExpenses()).thenReturn(ExpensesFactory.getExpenseResponses())
            whenever(expensesMapper.map(any())).thenReturn(ExpensesFactory.getExpense())
            val expected = listOf(ExpensesFactory.getExpense(), ExpensesFactory.getExpense())

            val actual = repository.getExpenses()

            assertEquals(expected, actual)
            verify(expensesService).getExpenses()
            verify(expensesMapper, times(2)).map(any())
        }
    }
}