package otus.demo.totalcoverage.baseexpenses

import io.reactivex.Single
import otus.demo.totalcoverage.addexpense.AddExpenseRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ExpensesService {

    @GET("expenses")
    suspend fun getExpenses(): List<ExpenseResponse>

    @POST("expenses")
    fun addExpense(@Body addExpenseRequest: AddExpenseRequest): Single<ExpenseResponse>
}