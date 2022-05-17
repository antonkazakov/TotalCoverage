package otus.demo.totalcoverage.expensesfilter

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import otus.demo.totalcoverage.R
import otus.demo.totalcoverage.baseexpenses.Category
import otus.demo.totalcoverage.expensesfilter.Filter
import otus.demo.totalcoverage.expensesfilter.FiltersFragment

@RunWith(AndroidJUnit4::class)
class FiltersFragmentTest {

    @Test
    fun `23`() {
        //given:
        val expected = Filter(listOf(Category.BARS), (0L..2000L))
        val navController = TestNavHostController(
            ApplicationProvider.getApplicationContext()
        )
        val titleScenario = launchFragmentInContainer<FiltersFragment>()
        titleScenario.onFragment { fragment ->
            navController.setGraph(R.navigation.nav_graph)
            Navigation.setViewNavController(fragment.requireView(), navController)
        }

        //when:
        onView(ViewMatchers.withId(R.id.submit_filters_button)).perform(ViewActions.click())

        //then 'Filter equal to expected':
        titleScenario.onFragment { fragment ->
            fragment.parentFragmentManager.setFragmentResultListener(
                "FILTERED_EXPENSES", fragment
            ) { _, b ->
                assertEquals(expected, b.get("FILTERS_KEY"))
            }
        }

        //and 'destination is expensesListFragment':
        assertEquals(
            navController.currentDestination?.id,
            R.id.expensesListFragment
        )
    }
}