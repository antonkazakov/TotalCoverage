package otus.demo.totalcoverage.featurefilters

import androidx.fragment.app.FragmentManager
import otus.demo.totalcoverage.featurefiltersapi.FiltersMediator
import javax.inject.Inject

class FiltersMediatorImpl @Inject constructor() : FiltersMediator {

    override fun openFiltersScreen(fragmentManager: FragmentManager) {
        FiltersFragment().show(fragmentManager, "filters")
    }
}