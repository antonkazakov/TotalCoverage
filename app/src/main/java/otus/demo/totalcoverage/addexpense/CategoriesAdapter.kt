package otus.demo.totalcoverage.addexpense

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import otus.demo.totalcoverage.R
import otus.demo.totalcoverage.baseexpenses.Category

class CategoriesAdapter(
    private val action: (category: Category) -> Unit
) : RecyclerView.Adapter<CategoriesAdapter.CategoriesVH>() {

    class CategoriesVH(private val categoryItem: CategoryItem) :
        RecyclerView.ViewHolder(categoryItem) {

        fun populate(category: Category) {
            categoryItem.populate(category)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesVH {
        return CategoriesVH(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.category_item, parent, false) as CategoryItem
        )
    }

    override fun onBindViewHolder(holder: CategoriesVH, position: Int) {
        holder.populate(Category.values()[holder.adapterPosition])
        holder.itemView.setOnClickListener {
            action(Category.values()[holder.adapterPosition])
        }
    }

    override fun getItemCount(): Int {
        return Category.values().size
    }
}

