package demo.currency.myapplication.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import demo.currency.myapplication.model.TradeSide

class QuoteViewModel : ViewModel() {
    private val _displaySummary = MutableLiveData<String>()
    val displaySummary: LiveData<String> = _displaySummary

    private val _isPreviewEnabled = MutableLiveData<Boolean>(false)
    val isPreviewEnabled: LiveData<Boolean> = _isPreviewEnabled

    private var side = TradeSide.BUY
    private var price: String = ""

    fun selectSide(side: TradeSide) {
        this.side = side
    }

    fun setPrice(price: String) {
        this.price = price
        _isPreviewEnabled.value = price.isNotBlank()
    }

    fun previewButtonClick() {
        _displaySummary.value = "You choose to $side @$${price}"
    }

}