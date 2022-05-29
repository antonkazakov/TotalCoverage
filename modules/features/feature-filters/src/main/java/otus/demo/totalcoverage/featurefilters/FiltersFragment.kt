package otus.demo.totalcoverage.featurefilters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.widget.PopupMenu
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import otus.demo.totalcoverage.coreapi.Category
import otus.demo.totalcoverage.coreapi.Filter
import otus.demo.totalcoverage.coreapi.Sort

const val FILTERS_KEY = "FILTERS_KEY"

class FiltersFragment : BottomSheetDialogFragment() {

    private lateinit var submitButton: Button
    private lateinit var clearButton: Button
    private lateinit var categoryButton: Button
    private lateinit var ascButton: Button
    private lateinit var descButton: Button
    private lateinit var dateDescButton: Button
    private lateinit var dateAscButton: Button

    private var sort: Sort = Sort.DESC_DATE
    private var categories = Category.values().toList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.filters_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViews(view)

        ascButton.setOnClickListener {
            sort = Sort.ASC
        }
        descButton.setOnClickListener {
            sort = Sort.DESC
        }
        dateDescButton.setOnClickListener {
            sort = Sort.DESC_DATE
        }
        dateAscButton.setOnClickListener {
            sort = Sort.ASC_DATE
        }
        submitButton.setOnClickListener {
            setFragmentResult(
                "FILTERED_KEY",
                bundleOf("FILTERS_KEY" to Filter(sort = sort, categories = categories))
            )
            parentFragmentManager.popBackStack()
        }
        clearButton.setOnClickListener {
            setFragmentResult(
                "FILTERED_KEY",
                bundleOf("FILTERS_KEY" to Filter())
            )
            parentFragmentManager.popBackStack()
        }
        categoryButton.setOnClickListener {
            showMenu()
        }
    }

    private fun bindViews(view: View) {
        submitButton = view.findViewById(R.id.submit_filters_button)
        categoryButton = view.findViewById(R.id.category_button)
        ascButton = view.findViewById(R.id.asc_button)
        descButton = view.findViewById(R.id.desc_button)
        dateAscButton = view.findViewById(R.id.asc_date_button)
        dateDescButton = view.findViewById(R.id.desc_date_button)
        clearButton = view.findViewById(R.id.clear_filters_button)
    }

    private fun showMenu() {
        val popup = PopupMenu(requireContext(), categoryButton)
        popup.menuInflater.inflate(R.menu.dropdownmenu, popup.menu)

        popup.setOnMenuItemClickListener { menuItem: MenuItem ->
            when (menuItem.itemId) {
                R.id.foods -> categories = listOf(Category.FOOD)
                R.id.transport -> categories = listOf(Category.TRANSPORT)
                R.id.travel -> categories = listOf(Category.TRAVEL)
                R.id.health -> categories = listOf(Category.HEALTH)
                R.id.bars -> categories = listOf(Category.BARS)
            }
            true
        }
        popup.show()
    }
}