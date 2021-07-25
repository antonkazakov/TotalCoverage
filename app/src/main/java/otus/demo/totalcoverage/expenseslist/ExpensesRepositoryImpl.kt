package otus.demo.totalcoverage.expenseslist

import otus.demo.totalcoverage.baseexpenses.Expense
import otus.demo.totalcoverage.baseexpenses.ExpensesService
import otus.demo.totalcoverage.utils.NeedsTesting
import javax.inject.Inject

@NeedsTesting
class ExpensesRepositoryImpl @Inject constructor(
    private val expensesService: ExpensesService,
    private val expensesMapper: ExpensesMapper
) : ExpensesRepository {

    override suspend fun getExpenses(): List<Expense> {
        return expensesService.getExpenses()
            .map { expensesMapper.map(it) }
    }
}

interface ExpensesRepository {

    suspend fun getExpenses(): List<Expense>
}
