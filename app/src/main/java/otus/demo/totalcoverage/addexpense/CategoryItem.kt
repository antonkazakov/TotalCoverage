package otus.demo.totalcoverage.addexpense

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.ImageView
import otus.demo.totalcoverage.R
import otus.demo.totalcoverage.baseexpenses.Category

class CategoryItem @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    fun populate(categoryItem: Category) {
        val icon = when (categoryItem) {
            Category.FOOD -> R.drawable.foods
            Category.HEALTH -> R.drawable.health
            Category.TRANSPORT -> R.drawable.transport
            Category.BARS -> R.drawable.beer
            Category.TRAVEL -> R.drawable.travel
        }
        findViewById<ImageView>(R.id.imageView)
            .setImageDrawable(context.getDrawable(icon))
    }
}