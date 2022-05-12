package otus.demo.totalcoverage.expensesfilter

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.runner.AndroidJUnit4
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import otus.demo.totalcoverage.ContainerActivity
import otus.demo.totalcoverage.R
import otus.demo.totalcoverage.baseexpenses.Category

@RunWith(RobolectricTestRunner::class)
@Config(instrumentedPackages = ["androidx.loader.content"])
class FiltersFragmentTest {

    @Test
    fun `should send fragment result with chosen filters and navigate up`() {
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