package otus.demo.totalcoverage.feature_expenseslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import otus.demo.totalcoverage.coreapi.Expense
import otus.demo.totalcoverage.coreapi.Filter
import javax.inject.Inject

@otus.demo.totalcoverage.coreapi.Open
class ExpensesViewModel constructor(
    private val filtersInteractor: FiltersInteractor,
    private val expensesRepository: ExpensesRepository,
    private val expensesMapper: otus.demo.totalcoverage.baseexpenses.ExpensesMapper,
    @IO private val ioDispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val _stateFlow: MutableStateFlow<Result> = MutableStateFlow(Empty)
    val stateFlow: StateFlow<Result> = _stateFlow

    fun getExpenses() {
        viewModelScope.launch {
            try {
                val expenses = withContext(ioDispatcher) {
                    expensesRepository.getExpenses()
                }
                if (expenses.isNotEmpty()) {
                    _stateFlow.emit(Success(expenses))
                } else {
                    _stateFlow.emit(Empty)
                }
            } catch (ex: Exception) {
                when (ex) {
                    is RuntimeException -> _stateFlow.emit(Error(ex))
                }
            }
        }
    }

    fun getFilteredExpenses(filter: Filter) {
        viewModelScope.launch {
            try {
                val expenses = withContext(ioDispatcher) {
                    filtersInteractor.getFilteredExpenses(filter)
                        .map { expensesMapper.map(it) }
                }
                _stateFlow.emit(Success(expenses))
            } catch (ex: Exception) {
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

@otus.demo.totalcoverage.coreapi.Open
class ExpensesViewModelFactory @Inject constructor(
    private val filtersInteractor: FiltersInteractor,
    private val expensesRepository: ExpensesRepositoryImpl,
    private val expensesMapper: otus.demo.totalcoverage.baseexpenses.ExpensesMapper,
    @IO private val ioCoroutineDispatcher: CoroutineDispatcher
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ExpensesViewModel::class.java))
            return ExpensesViewModel(
                filtersInteractor,
                expensesRepository,
                expensesMapper,
                ioCoroutineDispatcher
            ) as T
        else throw IllegalArgumentException()
    }
}