package otus.demo.totalcoverage.addexpenses

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import otus.demo.totalcoverage.coreapi.Category
import otus.demo.totalcoverage.coreapi.Expense
import otus.demo.totalcoverage.coreapi.Open
import java.io.Serializable
import javax.inject.Inject

@Open
class AddExpenseViewModel
@Inject constructor(
    private val addExpensesInteractor: AddExpensesInteractor,
) : ViewModel() {

    private val _liveData: MutableLiveData<AddExpenseResult> = MutableLiveData()
    var liveData: LiveData<AddExpenseResult> = _liveData

    var disposable: Disposable? = null

    fun addExpense(
        title: String,
        amount: String,
        category: Category,
        comment: String? = null
    ) {
        disposable = addExpensesInteractor.addExpense(title, amount.toLong(), category, comment)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ _liveData.value = Success(it) }, { _liveData.value = Error(it) })
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }
}

@Open
class AddExpenseViewModelModelFactory @Inject constructor(
    private val addExpensesInteractor: AddExpensesInteractor
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddExpenseViewModel::class.java))
            return AddExpenseViewModel(addExpensesInteractor) as T
        else throw IllegalArgumentException()
    }
}

sealed class AddExpenseResult : Serializable
data class Error(val throwable: Throwable?) : AddExpenseResult()
data class Success(val value: Expense) : AddExpenseResult()