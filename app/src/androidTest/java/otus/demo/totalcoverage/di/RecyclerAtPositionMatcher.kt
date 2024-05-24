package otus.demo.totalcoverage.di

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.matcher.BoundedMatcher
import org.hamcrest.Description
import org.hamcrest.Matcher

class RecyclerAtPositionMatcher(
    private val position: Int,
    private val matcher: Matcher<View>
) : BoundedMatcher<View, RecyclerView>(RecyclerView::class.java) {

    override fun describeTo(description: Description) {
    }

    override fun matchesSafely(item: RecyclerView): Boolean {
        return matcher.matches(item.findViewHolderForAdapterPosition(position)!!.itemView)
    }
}

fun atRecyclerPosition(position: Int, matcher: Matcher<View>) =
    RecyclerAtPositionMatcher(position, matcher)

class RecyclerSizeMatcher(
    private val size:Int
) : BoundedMatcher<View, RecyclerView>(RecyclerView::class.java) {

    override fun describeTo(description: Description) {
        description.appendText("not matches")
    }

    override fun matchesSafely(item: RecyclerView): Boolean {
        return item.adapter?.itemCount == size
    }
}

fun size(size: Int) = RecyclerSizeMatcher(size)