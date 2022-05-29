package otus.demo.totalcoverage.feature_expenseslist

import otus.demo.totalcoverage.baseexpenses.ExpensesMapper
import otus.demo.totalcoverage.baseexpenses.ExpensesService
import otus.demo.totalcoverage.coreapi.Expense
import javax.inject.Inject

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
