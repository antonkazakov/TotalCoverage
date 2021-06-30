package otus.demo.totalcoverage.expenseslist

import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import otus.demo.totalcoverage.ContainerActivity
import otus.demo.totalcoverage.R

@RunWith(AndroidJUnit4::class)
class ExpensesFragmentTestUI {

    @get:Rule
    var activityRule: ActivityScenarioRule<ContainerActivity> =
        ActivityScenarioRule(ContainerActivity::class.java)

    @Test
    fun shouldAddNewExpense() {
        onView(withId(R.id.add_expense_fab)).perform(
            click()
        )
        onView(withId(R.id.amount_edittext)).perform(typeText("10000"))
        onView(withId(R.id.title_edittext)).perform(typeText("Some expense22"))
        onView(withId(R.id.comment_edittext)).perform(typeText("Some comment"))
        onView(withId(R.id.submit_button)).perform(
            click()
        )

        onView(withId(R.id.expenses_recycler))
            .perform(RecyclerViewActions.actionOnItemAtPosition<ExpensesViewHolder>(0, scrollTo()))
            .check(matches(hasDescendant(withText("Some expense22"))))
    }

    @Test
    fun shouldFilterExpense() {
        Espresso.openContextualActionModeOverflowMenu()
        onView(withText("filters")).perform(
            click()
        )
        onView(withText("Сначала новые покупки")).perform(click())
        onView(withId(R.id.title_edittext)).perform(typeText("Some expense22"))
        onView(withId(R.id.comment_edittext)).perform(typeText("Some comment"))
        onView(withId(R.id.submit_button)).perform(
            click()
        )

        onView(withId(R.id.expenses_recycler))
            .perform(RecyclerViewActions.actionOnItemAtPosition<ExpensesViewHolder>(0, scrollTo()))
            .check(matches(hasDescendant(withText("Some expense22"))))
    }
}