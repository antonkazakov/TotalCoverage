package otus.demo.totalcoverage.expenseslist

import androidx.core.os.bundleOf
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.hamcrest.Matchers
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.matches
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import org.robolectric.shadows.ShadowToast
import otus.demo.totalcoverage.R
import otus.demo.totalcoverage.baseexpenses.Category
import otus.demo.totalcoverage.expensesfilter.Filter
import otus.demo.totalcoverage.testutils.ExpensesFactory
import java.lang.RuntimeException
import java.util.regex.Pattern.matches

@RunWith(AndroidJUnit4::class)
class ExpensesFragmentTest {

    private val expensesViewModel: ExpensesViewModel = mock()

    @Test
    fun `should show Toast with message when Error emited`() {
        runBlocking {
            //given:
            val flow = MutableStateFlow<Result>(Empty)
            whenever(expensesViewModel.stateFlow).thenReturn(flow)

            val factoryMock = mock<ExpensesViewModelFactory>()
            whenever(factoryMock.create<ExpensesViewModel>(any())).thenReturn(expensesViewModel)

            val fragmentScenario =
                launchFragmentInContainer<ExpensesFragment>(initialState = Lifecycle.State.CREATED)
            fragmentScenario.onFragment { fragment ->
                fragment.viewModelFactory = factoryMock
                fragmentScenario.moveToState(Lifecycle.State.STARTED)
            }

            //when:
            flow.emit(Error(RuntimeException("Some Error")))

            //then:
            assertEquals("Some Error", ShadowToast.getTextOfLatestToast())
        }
    }

    @Test
    fun `should notify adapter when Success emited`() {
        runBlocking {
            //given:
            val adapterMock = mock<ExpensesAdapter>()
            val expected = ExpensesFactory.getExpenses()
            val flow = MutableStateFlow<Result>(Empty)
            whenever(expensesViewModel.stateFlow).thenReturn(flow)

            val factoryMock = mock<ExpensesViewModelFactory>()
            whenever(factoryMock.create<ExpensesViewModel>(any())).thenReturn(expensesViewModel)

            val fragmentScenario =
                launchFragmentInContainer<ExpensesFragment>(initialState = Lifecycle.State.CREATED)
            fragmentScenario.onFragment { fragment ->
                fragment.adapter = adapterMock
                fragment.viewModelFactory = factoryMock
                fragmentScenario.moveToState(Lifecycle.State.STARTED)
            }

            //when:
            flow.emit(Success(expected))

            //then:
            verify(adapterMock).addItems(expected)
        }
    }

    @Test
    fun `should show empty text when Empty emited`() {
        runBlocking {
            //given:
            val adapterMock = mock<ExpensesAdapter>()

            val flow = MutableStateFlow<Result>(Empty)
            whenever(expensesViewModel.stateFlow).thenReturn(flow)

            val factoryMock = mock<ExpensesViewModelFactory>()
            whenever(factoryMock.create<ExpensesViewModel>(any())).thenReturn(expensesViewModel)

            val fragmentScenario =
                launchFragmentInContainer<ExpensesFragment>(initialState = Lifecycle.State.CREATED)
            fragmentScenario.onFragment { fragment ->
                fragment.adapter = adapterMock
                fragment.viewModelFactory = factoryMock
                fragmentScenario.moveToState(Lifecycle.State.STARTED)
            }

            //when:
            flow.emit(Empty)

            //then:
            Espresso.onView(ViewMatchers.withId(R.id.empty_text))
                .check(matches(ViewMatchers.isDisplayed()))
        }
    }

    @Test
    fun `should navigate to add expenses fragment when add button clicked`() {
        val fragmentScenario =
            launchFragmentInContainer<ExpensesFragment>()
        val navController = TestNavHostController(
            ApplicationProvider.getApplicationContext()
        )

        fragmentScenario.onFragment { fragment ->
            navController.setGraph(R.navigation.nav_graph)
            Navigation.setViewNavController(fragment.requireView(), navController)
        }

        Espresso.onView(ViewMatchers.withId(R.id.add_expense_fab)).perform(
            ViewActions.click()
        )

        //and:
        assertEquals(
            navController.currentDestination?.id,
            R.id.addExpenseFragment
        )
    }

    @Test
    fun `should navigate to  filters fragment when filters menu button clicked`() {
        //given:
        val fragmentScenario =
            launchFragmentInContainer<ExpensesFragment>()
        val navController = TestNavHostController(
            ApplicationProvider.getApplicationContext()
        )

        fragmentScenario.onFragment { fragment ->
            navController.setGraph(R.navigation.nav_graph)
            Navigation.setViewNavController(fragment.requireView(), navController)
        }

        //when:
        Espresso.openContextualActionModeOverflowMenu()
        Espresso.onView(ViewMatchers.withText("filters")).perform(
            ViewActions.click()
        )

        //and:
        assertEquals(
            navController.currentDestination?.id,
            R.id.filtersFragment
        )
    }

    @Test
    fun `should add call addItem when result with EXPENSES key received`() {
        //given:
        val adapterMock = mock<ExpensesAdapter>()

        val expected = ExpensesFactory.getExpense()

        val fragmentScenario =
            launchFragmentInContainer<ExpensesFragment>()

        fragmentScenario.onFragment { fragment ->
            fragment.adapter = adapterMock
        }

        //when:
        fragmentScenario.onFragment { fragment ->
            fragment.parentFragmentManager.setFragmentResult(
                "EXPENSES", bundleOf(
                    "EXPENSE_KEY" to expected
                )
            )
        }

        //and:
        verify(adapterMock).addItem(expected)
    }

    @Test
    fun `should add call addItem when result with FILTERS key received`() {
        //given:
        val adapterMock = mock<ExpensesAdapter>()
        val expected = Filter(listOf(Category.BARS, Category.FOOD))

        val factoryMock = mock<ExpensesViewModelFactory>()
        whenever(factoryMock.create<ExpensesViewModel>(any())).thenReturn(expensesViewModel)

        val fragmentScenario =
            launchFragmentInContainer<ExpensesFragment>(initialState = Lifecycle.State.CREATED)
        fragmentScenario.onFragment { fragment ->
            fragment.adapter = adapterMock
            fragment.viewModelFactory = factoryMock
            fragmentScenario.moveToState(Lifecycle.State.STARTED)
        }

        //when:
        fragmentScenario.onFragment { fragment ->
            fragment.parentFragmentManager.setFragmentResult(
                "FILTERED_EXPENSES", bundleOf(
                    "FILTERS_KEY" to expected
                )
            )
        }

        //then:
        verify(expensesViewModel).getFilteredExpenses(expected)
    }
}