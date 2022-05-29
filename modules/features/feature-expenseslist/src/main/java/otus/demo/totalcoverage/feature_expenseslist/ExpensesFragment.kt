package otus.demo.totalcoverage.feature_expenseslist

import android.os.Bundle
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.flow.collect
import otus.demo.totalcoverage.coreapi.Expense
import otus.demo.totalcoverage.coreapi.Filter
import otus.demo.totalcoverage.coreapi.ProvidersHolder
import otus.demo.totalcoverage.featureexpensesapi.AddExpensesMediator
import otus.demo.totalcoverage.featurefiltersapi.FiltersMediator
import javax.inject.Inject

const val KEY = "EXPENSES"
const val EXPENSE_KEY = "EXPENSE_KEY"

class ExpensesFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var adapter: ExpensesAdapter

    @Inject
    lateinit var filtersMediator: FiltersMediator

    @Inject
    lateinit var addExpensesMediator: AddExpensesMediator

    private lateinit var expensesViewModel: ExpensesViewModel

    private lateinit var expensesRecycler: RecyclerView
    private lateinit var emptyText: TextView
    private lateinit var addExpenseButton: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ExpensesComponent.create(
            ((requireActivity().application) as ProvidersHolder).getAggregatingProvider()
        ).inject(this)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        emptyText = view.findViewById(R.id.empty_text)
        expensesRecycler = view.findViewById(R.id.expenses_recycler)
        expensesRecycler.adapter = adapter
        addExpenseButton = view.findViewById(R.id.add_expense_fab)
        addExpenseButton.setOnClickListener {
            addExpensesMediator.openAddExpensesScreen(parentFragmentManager, R.id.host_container)
        }

        setFragmentResultListener(KEY) { _, bundle ->
            val expense = bundle.getSerializable(EXPENSE_KEY) as Expense
            adapter.addItem(expense)
        }

        setFragmentResultListener("FILTERED_KEY") { _, bundle ->
            val filter = bundle.getSerializable("FILTERS_KEY") as Filter
            expensesViewModel.getFilteredExpenses(filter)
        }

        expensesViewModel =
            ViewModelProvider(this, viewModelFactory).get(ExpensesViewModel::class.java)
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
            filtersMediator.openFiltersScreen(childFragmentManager)
            true
        }
    }
}