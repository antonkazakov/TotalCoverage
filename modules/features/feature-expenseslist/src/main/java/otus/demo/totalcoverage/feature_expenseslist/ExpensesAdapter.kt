package otus.demo.totalcoverage.feature_expenseslist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import otus.demo.totalcoverage.coreapi.Expense
import javax.inject.Inject

@otus.demo.totalcoverage.coreapi.Open
class ExpensesAdapter @Inject constructor() :
    RecyclerView.Adapter<ExpensesViewHolder>() {

    val items: MutableList<Expense> = arrayListOf()

    fun addItems(expenses: List<Expense>) {
        items.clear()
        items.addAll(expenses)
        notifyDataSetChanged()
    }

    fun addItem(expense: Expense) {
        items.add(expense)
        notifyItemChanged(items.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ExpensesViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.expenses_item_layout, parent, false) as ExpensesItemView
    )

    override fun onBindViewHolder(holder: ExpensesViewHolder, position: Int) {
        holder.populate(items[position])
    }

    override fun getItemCount() = items.size
}

class ExpensesViewHolder(private val expensesItemView: ExpensesItemView) :
    RecyclerView.ViewHolder(expensesItemView) {

    fun populate(expense: Expense) {
        expensesItemView.populate(expense)
    }
}