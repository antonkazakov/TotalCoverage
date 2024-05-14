package otus.demo.totalcoverage.expenseslist

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.test.espresso.idling.CountingIdlingResource
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import otus.demo.totalcoverage.Open
import otus.demo.totalcoverage.baseexpenses.Expense
import otus.demo.totalcoverage.expensesfilter.Filter
import otus.demo.totalcoverage.utils.NeedsTesting
import java.util.Optional
import javax.inject.Inject

@NeedsTesting
@Open
@HiltViewModel
class ExpensesViewModel @Inject constructor(
    private val filtersInteractor: FiltersInteractor,
    private val expensesRepository: ExpensesRepository,
    private val expensesMapper: ExpensesMapper,
    @IO private val ioDispatcher: CoroutineDispatcher,
    private val idlingResource: Optional<CountingIdlingResource>,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _stateFlow: MutableStateFlow<Result> = MutableStateFlow(Empty)
    val stateFlow: StateFlow<Result> = _stateFlow

    fun getExpenses() {
        viewModelScope.launch {
            try {
                idlingResource.ifPresent { it.increment() }
                val expenses = withContext(ioDispatcher) {
                    expensesRepository.getExpenses()
                }
                idlingResource.ifPresent { it.decrement() }
                if (expenses.isNotEmpty()) {
                    _stateFlow.emit(Success(expenses))
                } else {
                    _stateFlow.emit(Empty)
                }
            } catch (ex: Exception) {
                idlingResource.ifPresent { it.decrement() }
                when (ex) {
                    is RuntimeException -> _stateFlow.emit(Error(ex))
                }
            }
        }
    }

    fun getFilteredExpenses(filter: Filter) {
        viewModelScope.launch {
            try {
                idlingResource.ifPresent { it.increment() }
                val expenses = withContext(ioDispatcher) {
                    filtersInteractor.getFilteredExpenses(filter)
                        .map { expensesMapper.map(it) }
                }
                idlingResource.ifPresent { it.decrement() }
                _stateFlow.emit(Success(expenses))
            } catch (ex: Exception) {
                idlingResource.ifPresent { it.decrement() }
                when (ex) {
                    is RuntimeException -> _stateFlow.emit(Error(ex))
                }
            }
        }
    }
}

sealed class Result
data class Error(val throwable: Throwable?) : Result()
object Empty : Result()
data class Success(val value: List<Expense>) : Result()