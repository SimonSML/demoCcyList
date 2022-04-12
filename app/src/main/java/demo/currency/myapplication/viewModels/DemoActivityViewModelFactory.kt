package demo.currency.myapplication.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import demo.currency.myapplication.repositories.CurrencyInfoRepoImpl
import kotlinx.coroutines.CoroutineDispatcher

//Not used as now using Koin
class DemoActivityViewModelFactory(
    private val repo: CurrencyInfoRepoImpl,
    private val defaultDispatcher: CoroutineDispatcher
) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DemoActivityViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DemoActivityViewModel(repo, defaultDispatcher) as T
        }
        throw throw IllegalArgumentException(
            "Unknown ViewModel class: ${modelClass.name}"
        )
    }
}