package otus.demo.totalcoverage.expenseslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.test.espresso.idling.CountingIdlingResource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import otus.demo.totalcoverage.Open
import otus.demo.totalcoverage.baseexpenses.Expense
import otus.demo.totalcoverage.expensesfilter.Filter
import java.util.Optional
import javax.inject.Inject

@Open
class ExpensesViewModel(
    private val filtersInteractor: FiltersInteractor,
    private val expensesRepository: ExpensesRepository,
    private val expensesMapper: ExpensesMapper,
    @IO private val ioDispatcher: CoroutineDispatcher,
    private val idlingResource: Optional<CountingIdlingResource>
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
data object Empty : Result()
data class Success(val value: List<Expense>) : Result()

@Open
class ExpensesViewModelFactory @Inject constructor(
    private val filtersInteractor: FiltersInteractor,
    private val expensesRepository: ExpensesRepositoryImpl,
    private val expensesMapper: ExpensesMapper,
    @IO private val ioCoroutineDispatcher: CoroutineDispatcher,
    private val idlingResource: Optional<CountingIdlingResource>
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ExpensesViewModel::class.java))
            return ExpensesViewModel(
                filtersInteractor,
                expensesRepository,
                expensesMapper,
                ioCoroutineDispatcher,
                idlingResource
            ) as T
        else throw IllegalArgumentException()
    }
}