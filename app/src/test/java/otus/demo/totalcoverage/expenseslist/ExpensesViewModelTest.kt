package otus.demo.totalcoverage.expenseslist

import app.cash.turbine.test
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import otus.demo.totalcoverage.baseexpenses.Category
import otus.demo.totalcoverage.baseexpenses.Expense
import kotlin.time.ExperimentalTime

class ExpensesViewModelTest {

    private val filtersInteractor: FiltersInteractor = mock()
    private val expensesRepository: ExpensesRepository = mock()
    private val expensesMapper: ExpensesMapper = mock()
    private val testDispatcher = TestCoroutineDispatcher()

    private val expensesViewModel =
        ExpensesViewModel(
            filtersInteractor,
            expensesRepository,
            expensesMapper,
            testDispatcher
        )

    @Before
    fun before() {
        Dispatchers.setMain(testDispatcher)
    }

    @ExperimentalTime
    @Test
    fun `should emit Success with non empty expenses list`() {
        runTest {
            //given:
            val expected = Expense(
                2,
                "Some sport equipment",
                Category.SPORT,
                amount = 120,
                date = "20-06-2021"
            )
            whenever(expensesRepository.getExpenses()).thenReturn(
                listOf(expected)
            )

            expensesViewModel.getExpenses()
            expensesViewModel.stateFlow.test {
                Assert.assertEquals(Success(listOf(expected)), expectMostRecentItem())
            }
        }
    }

    @ExperimentalTime
    @Test
    fun `should emit instance of Empty when expenses are empty`() {
        runTest {
            whenever(expensesRepository.getExpenses()).thenReturn(
                emptyList()
            )

            expensesViewModel.getExpenses()
            expensesViewModel.stateFlow.test {
                Assert.assertEquals(Empty, expectMostRecentItem())
            }
        }
    }

    @ExperimentalTime
    @Test
    fun `should emit instance of Error when IOException was thrown`() {
        runTest {
            val expectedException = RuntimeException("Error")
            whenever(expensesRepository.getExpenses()).thenThrow(expectedException)

            expensesViewModel.getExpenses()
            expensesViewModel.stateFlow.test {
                //проверить почему не работает при смене тест диспатчера
                Assert.assertEquals(Error(expectedException), awaitItem())
            }
        }
    }

    @After
    fun after() {
        Dispatchers.resetMain()
    }
}