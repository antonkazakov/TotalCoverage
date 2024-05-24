package otus.demo.totalcoverage.di

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.idling.CountingIdlingResource
import androidx.test.espresso.matcher.RootMatchers.isPlatformPopup
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.hamcrest.Matchers
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import otus.demo.totalcoverage.ContainerActivity
import otus.demo.totalcoverage.R
import java.util.Optional
import javax.inject.Inject

class UserFlowSimple {

    @get:Rule
    val activityRule = ActivityScenarioRule(ContainerActivity::class.java)

    @Inject
    lateinit var countingIdlingResource: Optional<CountingIdlingResource>

    @Before
    fun before() {
        activityRule.scenario.onActivity {
            ((it.application as TestApp).getAppComponent() as TestAppComponent).inject(this)
        }
        IdlingRegistry.getInstance().register(countingIdlingResource.get())
    }

    @Test
    fun simpleFlow() {
        onView(withId(R.id.empty_text))
            .check(
                matches(
                    Matchers.allOf(
                        ViewMatchers.isDisplayed(),
                        withText("No expenses :)")
                    )
                )
            )
    }

    @Test
    fun someUserFlow() {
        onView(withId(R.id.add_expense_fab))
            .perform(click())

        onView(withId(R.id.title_edittext))
            .perform(typeText("Food"))
        onView(withId(R.id.amount_edittext))
            .perform(typeText("2000"))
        onView(withId(R.id.comment_edittext))
            .perform(typeText("was good"))

        onView(withId(R.id.category_recycler))
            .perform(
                actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    0,
                    click()
                )
            )
        onView(withId(R.id.submit_button))
            .perform(click())



        onView(withId(R.id.add_expense_fab))
            .perform(click())
        onView(withId(R.id.title_edittext))
            .perform(typeText("Vacation"))
        onView(withId(R.id.amount_edittext))
            .perform(typeText("100000"))
        onView(withId(R.id.comment_edittext))
            .perform(typeText("was super good"))

        onView(withId(R.id.category_recycler))
            .perform(
                actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    4,
                    click()
                )
            )
        onView(withId(R.id.submit_button))
            .perform(click())

        onView(withId(R.id.filters)).perform(click())
        onView(withId(R.id.category_button)).perform(click())

        onView(withText("Путешествия"))
            .inRoot(isPlatformPopup())
            .perform(click())

        onView(withId(R.id.submit_filters_button))
            .perform(click())

        onView(withId(R.id.expenses_recycler))
            .check(
                matches(
                    atRecyclerPosition(
                        0,
                        Matchers.allOf(
                            hasDescendant(withText("Vacation")),
                            hasDescendant(withText("100000 ₽")),
                            hasDescendant(withText("was super good"))
                        )
                    )
                )
            )
        onView(withId(R.id.expenses_recycler)).check(
            matches(size(1))
        )
    }

    @After
    fun after() {
        IdlingRegistry.getInstance().unregister(countingIdlingResource.get())
    }
}
