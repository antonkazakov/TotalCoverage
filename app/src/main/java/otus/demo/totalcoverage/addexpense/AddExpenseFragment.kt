package otus.demo.totalcoverage.addexpense

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import otus.demo.totalcoverage.R
import otus.demo.totalcoverage.baseexpenses.Category
import otus.demo.totalcoverage.expenseslist.EXPENSE_KEY
import otus.demo.totalcoverage.expenseslist.KEY

@AndroidEntryPoint
class AddExpenseFragment : Fragment() {

    private val addExpenseViewModel: AddExpenseViewModel by viewModels()

    private lateinit var nameInput: EditText
    private lateinit var amountInput: EditText
    private lateinit var commentInput: EditText
    private lateinit var submitButton: Button
    private lateinit var recyclerView: RecyclerView
    private var category: Category = Category.FOOD

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.add_expenses_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.category_recycler)
        nameInput = view.findViewById(R.id.title_edittext)
        amountInput = view.findViewById(R.id.amount_edittext)
        commentInput = view.findViewById(R.id.comment_edittext)
        submitButton = view.findViewById(R.id.submit_button)

        recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        recyclerView.adapter = CategoriesAdapter() {
            category = it
        }

        submitButton.setOnClickListener {
            addExpenseViewModel.addExpense(
                nameInput.text.toString(),
                amountInput.text.toString(),
                category,
                commentInput.text.toString()
            )
        }
    }

    override fun onStart() {
        super.onStart()
        addExpenseViewModel.liveData.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Success -> {
                    setFragmentResult(
                        KEY,
                        bundleOf(EXPENSE_KEY to result.value)
                    )
                    findNavController().navigateUp()
                }

                is Error -> {
                    Toast.makeText(activity, result.throwable?.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}