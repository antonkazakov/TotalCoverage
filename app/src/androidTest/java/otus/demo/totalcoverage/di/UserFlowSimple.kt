package otus.demo.totalcoverage.di

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.base.IdlingResourceRegistry
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.idling.CountingIdlingResource
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.hamcrest.Matchers.allOf
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import otus.demo.totalcoverage.ContainerActivity
import otus.demo.totalcoverage.R
import javax.inject.Inject

class UserFlowSimple {

    @get:Rule
    val activityRule = ActivityScenarioRule(ContainerActivity::class.java)

    @Inject
    lateinit var idlingResource: CountingIdlingResource

    @Before
    fun init() {
        activityRule.scenario.onActivity {
            ((it.application as TestApp).getAppComponent() as TestAppComponent).inject(this)
        }
        IdlingRegistry.getInstance().register(idlingResource)
    }

    @Test
    fun shouldShowEmptyView() {
        onView(withId(R.id.empty_text))
            .check(
                matches(
                    allOf(
                        isDisplayed(),
                        withText("No expenses :)")
                    )
                )
            )
    }

    @Test
    fun userFlowTest() {
        onView(withId(R.id.add_expense_fab)).perform(click())

        onView(withId(R.id.title_edittext)).perform(typeText("Food"))
        onView(withId(R.id.amount_edittext)).perform(typeText("2000"))
        onView(withId(R.id.comment_edittext)).perform(typeText("was good"))

        onView(withId(R.id.category_recycler))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    0, click()
                )
            )

        onView(withId(R.id.submit_button)).perform(click())

        onView(withId(R.id.add_expense_fab)).perform(click())

        onView(withId(R.id.title_edittext)).perform(typeText("Vacation"))
        onView(withId(R.id.amount_edittext)).perform(typeText("100000"))
        onView(withId(R.id.comment_edittext)).perform(typeText("was super good"))

        onView(withId(R.id.category_recycler))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    4, click()
                )
            )

        onView(withId(R.id.submit_button)).perform(click())

        onView(withId(R.id.filters)).perform(click())

        onView(withId(R.id.category_button)).perform(click())

        onView(withText("Путешествия"))
            .inRoot(RootMatchers.isPlatformPopup())
            .perform(click())

        onView(withId(R.id.submit_filters_button)).perform(click())

        onView(withId(R.id.expenses_recycler))
            .check(
                matches(
                    atPosition(
                        0,
                        allOf(
                            hasDescendant(withText("Vacation")),
                            hasDescendant(withText("100000 ₽")),
                            hasDescendant(withText("was super good"))
                        )
                    )
                )
            )
    }


    @After
    fun after() {
        IdlingRegistry.getInstance().unregister(idlingResource)
    }
}
