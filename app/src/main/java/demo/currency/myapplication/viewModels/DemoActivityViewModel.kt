package demo.currency.myapplication.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import demo.currency.myapplication.model.CurrencyInfo
import demo.currency.myapplication.repositories.CurrencyInfoRepo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DemoActivityViewModel(
    private val repo: CurrencyInfoRepo,
    private val defaultDispatcher: CoroutineDispatcher
) : ViewModel() {

    private var fetching: Job? = null
    private var sorting: Job? = null

    private val _currencyInfoList = MutableLiveData<ArrayList<CurrencyInfo>>()
    val currencyInfoList: LiveData<ArrayList<CurrencyInfo>>
        get() = _currencyInfoList

    private val _disableSorting = MutableLiveData(true)
    val disableSorting: LiveData<Boolean>
        get() = _disableSorting

    fun fetchCurrencyList() {
        if (fetching?.isActive == true) {
            return
        }

        sorting?.cancel()
        _disableSorting.value = true

        fetching = viewModelScope.launch {
            val list = repo.getCurrencyList().toCollection(ArrayList())
            _currencyInfoList.value = list
            _disableSorting.value = false
        }
    }

    fun sortCurrentList() {
        if (sorting?.isActive == true) {
            return
        }
        _currencyInfoList.value?.let {
            sorting = viewModelScope.launch {
                withContext(defaultDispatcher) {
                    it.sortByDescending { it.id }
                }
                _currencyInfoList.value = it
            }
        }
    }
}