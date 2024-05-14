package otus.demo.totalcoverage.expenseslist

import android.os.Bundle
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint
import otus.demo.totalcoverage.R
import otus.demo.totalcoverage.baseexpenses.Expense
import otus.demo.totalcoverage.expensesfilter.FILTERS_KEY
import otus.demo.totalcoverage.expensesfilter.Filter
import javax.inject.Inject

const val KEY = "EXPENSES"
const val FILTERED_KEY = "FILTERED_EXPENSES"
const val EXPENSE_KEY = "EXPENSE_KEY"

@AndroidEntryPoint
class ExpensesFragment : Fragment() {

    @Inject
    lateinit var adapter: ExpensesAdapter

    private val expensesViewModel: ExpensesViewModel by viewModels()

    private lateinit var expensesRecycler: RecyclerView
    private lateinit var emptyText: TextView
    private lateinit var addExpenseButton: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        emptyText = view.findViewById(R.id.empty_text)
        expensesRecycler = view.findViewById(R.id.expenses_recycler)
        expensesRecycler.adapter = adapter
        addExpenseButton = view.findViewById(R.id.add_expense_fab)
        addExpenseButton.setOnClickListener {
            findNavController().navigate(R.id.action_expensesListFragment_to_addExpenseFragment)
        }

        setFragmentResultListener(KEY) { _, bundle ->
            val expense = bundle.getSerializable(EXPENSE_KEY) as Expense
            adapter.addItem(expense)
        }

        setFragmentResultListener(FILTERED_KEY) { _, bundle ->
            val filter = bundle.getSerializable(FILTERS_KEY) as Filter
            expensesViewModel.getFilteredExpenses(filter)
        }

        lifecycleScope.launchWhenStarted {
            expensesViewModel.stateFlow.collect { result ->
                when (result) {
                    is Success -> {
                        emptyText.visibility = View.GONE
                        adapter.addItems(result.value)
                    }

                    is Error -> {
                        Toast.makeText(activity, result.throwable?.message, Toast.LENGTH_LONG)
                            .show()
                    }

                    Empty -> {
                        emptyText.visibility = View.VISIBLE
                    }
                }
            }
        }
        expensesViewModel.getExpenses()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.expenses_list_layout, container, false)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.expenses_menu, menu)
        menu.findItem(R.id.filters).setOnMenuItemClickListener {
            findNavController().navigate(R.id.action_expensesListFragment_to_filtersFragment)
            true
        }
    }
}