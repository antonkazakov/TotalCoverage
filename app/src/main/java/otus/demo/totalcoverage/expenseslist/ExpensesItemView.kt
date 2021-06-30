package otus.demo.totalcoverage.expenseslist

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import otus.demo.totalcoverage.R
import otus.demo.totalcoverage.baseexpenses.Category
import otus.demo.totalcoverage.baseexpenses.Expense
import otus.demo.totalcoverage.utils.NeedsTesting

@NeedsTesting
class ExpensesItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private lateinit var nameText:TextView
    private lateinit var commentText:TextView
    private lateinit var amountText:TextView
    private lateinit var categoryImage:ImageView

    override fun onFinishInflate() {
        super.onFinishInflate()
        nameText = findViewById(R.id.tv_name)
        commentText = findViewById(R.id.tv_comment)
        amountText = findViewById(R.id.tv_amount)
        nameText = findViewById(R.id.tv_name)
    }

    fun populate(expense: Expense){
        nameText.text = expense.title
        commentText.text = expense.comment
        amountText.text = "${expense.amount} ₽"
    }
}