package otus.demo.totalcoverage.addexpense

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.MutableLiveData
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.*
import org.robolectric.annotation.Config
import org.robolectric.shadows.ShadowToast
import otus.demo.totalcoverage.R
import otus.demo.totalcoverage.baseexpenses.Category
import otus.demo.totalcoverage.baseexpenses.Expense

@RunWith(AndroidJUnit4::class)
@Config(instrumentedPackages = ["androidx.loader.content"])
class AddExpenseFragmentTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val viewModel = mock<otus.demo.totalcoverage.addexpenses.AddExpenseViewModel>()

    @Test
    fun `should call addExpense function when submit_button was clicked`() {
        val factory: otus.demo.totalcoverage.addexpenses.AddExpenseViewModelModelFactory = mock()
        whenever(factory.create<otus.demo.totalcoverage.addexpenses.AddExpenseViewModel>(any())).thenReturn(viewModel)
        val liveData = MutableLiveData<otus.demo.totalcoverage.addexpenses.AddExpenseResult>()
        whenever(viewModel.liveData).thenReturn(liveData)
        val fragmentScenario =
            launchFragmentInContainer<otus.demo.totalcoverage.addexpenses.AddExpenseFragment>(initialState = Lifecycle.State.CREATED)

        fragmentScenario.onFragment { fragment ->
            fragment.viewModelFactory = factory
            fragmentScenario.moveToState(Lifecycle.State.STARTED)
        }

        Espresso.onView(ViewMatchers.withId(R.id.amount_edittext))
            .perform(ViewActions.typeText("100"))
        Espresso.onView(ViewMatchers.withId(R.id.title_edittext))
            .perform(ViewActions.typeText("Some title"))
        Espresso.onView(ViewMatchers.withId(R.id.comment_edittext))
            .perform(ViewActions.typeText("Some comment"))

        Espresso.onView(ViewMatchers.withId(R.id.submit_button))
            .perform(ViewActions.click())

        verify(viewModel).addExpense("Some title", "100", Category.BARS, "Some comment")
    }

    @Test
    fun `should navigate up with payload when Success event was emited`() {
        //given:
        val expected = Expense(0L, "", Category.BARS, "", 1L, "")

        val liveData = MutableLiveData<otus.demo.totalcoverage.addexpenses.AddExpenseResult>()
        whenever(viewModel.liveData).thenReturn(liveData)

        val factoryMock = mock<otus.demo.totalcoverage.addexpenses.AddExpenseViewModelModelFactory>()
        whenever(factoryMock.create<otus.demo.totalcoverage.addexpenses.AddExpenseViewModel>(any())).thenReturn(viewModel)

        val fragmentScenario =
            launchFragmentInContainer<otus.demo.totalcoverage.addexpenses.AddExpenseFragment>(initialState = Lifecycle.State.CREATED)

        val navController = TestNavHostController(
            ApplicationProvider.getApplicationContext()
        )
        fragmentScenario.onFragment { fragment ->
            fragment.viewModelFactory = factoryMock
            navController.setGraph(R.navigation.nav_graph)
            fragmentScenario.moveToState(Lifecycle.State.STARTED)
            Navigation.setViewNavController(fragment.requireView(), navController)
        }

        //when:
        liveData.value = otus.demo.totalcoverage.addexpenses.Success(expected)

        //then:
        fragmentScenario.onFragment { fragment ->
            fragment.parentFragmentManager.setFragmentResultListener(
                "EXPENSES", fragment
            ) { _, b ->
                assertEquals(expected, b.get("EXPENSE_KEY"))
            }
        }

        //and:
        assertEquals(
            navController.currentDestination?.id,
            R.id.expensesListFragment
        )
    }

    @Test
    fun `should show Toast with message when Error event was emited`() {
        //given:
        val liveData = MutableLiveData<otus.demo.totalcoverage.addexpenses.AddExpenseResult>()
        whenever(viewModel.liveData).thenReturn(liveData)

        val factoryMock = mock<otus.demo.totalcoverage.addexpenses.AddExpenseViewModelModelFactory>()
        whenever(factoryMock.create<otus.demo.totalcoverage.addexpenses.AddExpenseViewModel>(any())).thenReturn(viewModel)

        val fragmentScenario =
            launchFragmentInContainer<otus.demo.totalcoverage.addexpenses.AddExpenseFragment>(initialState = Lifecycle.State.CREATED)
        val navController = TestNavHostController(
            ApplicationProvider.getApplicationContext()
        )
        fragmentScenario.onFragment { fragment ->
            fragment.viewModelFactory = factoryMock
            navController.setGraph(R.navigation.nav_graph)
            fragmentScenario.moveToState(Lifecycle.State.STARTED)
            Navigation.setViewNavController(fragment.requireView(), navController)

        }

        //when:
        liveData.value = otus.demo.totalcoverage.addexpenses.Error(RuntimeException("Some error"))

        //then 'show toast with expected message':
        assertEquals(
            "Some error", ShadowToast.getTextOfLatestToast()
        )
    }
}