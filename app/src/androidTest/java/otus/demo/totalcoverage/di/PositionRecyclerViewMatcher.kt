package otus.demo.totalcoverage.di

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.matcher.BoundedMatcher
import org.hamcrest.Description
import org.hamcrest.Matcher

class PositionRecyclerViewMatcher(
    private val position: Int,
    private val matcher: Matcher<View>
) : BoundedMatcher<View, RecyclerView>(RecyclerView::class.java) {

    override fun describeTo(description: Description?) {
        description?.appendText("not matches at some position")
    }

    override fun matchesSafely(item: RecyclerView): Boolean {
        return matcher.matches(item.findViewHolderForAdapterPosition(position)?.itemView)
    }
}

fun atPosition(position: Int, matcher: Matcher<View>) =
    PositionRecyclerViewMatcher(position, matcher)