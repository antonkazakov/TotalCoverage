package otus.demo.totalcoverage.expensesfilter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import otus.demo.totalcoverage.R
import otus.demo.totalcoverage.baseexpenses.Category
import otus.demo.totalcoverage.expenseslist.FILTERED_KEY

const val FILTERS_KEY = "FILTERS_KEY"

class FiltersFragment : BottomSheetDialogFragment() {

    private lateinit var submitButton: Button
    private lateinit var clearButton: Button
    private lateinit var ascButton: Button
    private lateinit var descButton: Button
    private lateinit var dateDescButton: Button
    private lateinit var dateAscButton: Button

    private var sort: Sort = Sort.DESC_DATE

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.filters_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        submitButton = view.findViewById(R.id.submit_filters_button)
        ascButton = view.findViewById(R.id.asc_button)
        descButton = view.findViewById(R.id.desc_button)
        dateAscButton = view.findViewById(R.id.asc_date_button)
        dateDescButton = view.findViewById(R.id.desc_date_button)
        clearButton = view.findViewById(R.id.clear_filters_button)
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
                FILTERED_KEY,
                bundleOf(FILTERS_KEY to Filter(listOf(Category.BARS), (0L..2000), sort))
            )
            findNavController().navigateUp()
        }
        clearButton.setOnClickListener {
            setFragmentResult(
                FILTERED_KEY,
                bundleOf(FILTERS_KEY to Filter())
            )
            findNavController().navigateUp()
        }
    }
}