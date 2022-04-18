package demo.currency.myapplication.model

import com.google.gson.annotations.SerializedName
import demo.currency.myapplication.utils.FormatUtils.formatPriceNumberDisplay
import demo.currency.myapplication.utils.FormatUtils.formatPriceNumberNoDecimal
import java.io.Serializable
import java.math.BigDecimal

data class CurrencyQuote(
    val exchange: String? = null,

    val currency: String? = null,

    val ticker: String? = null,

    val name: String? = null,

    @SerializedName("delayed_price")
    private val _price: BigDecimal? = null,

    val priceChange: BigDecimal? = null,

    @SerializedName("percentageChange")
    private val _percentageChange: BigDecimal? = null,

    @SerializedName("open_price")
    private val _open: BigDecimal? = null,

    @SerializedName("highest")
    private val _high: BigDecimal? = null,

    @SerializedName("lowest")
    private val _low: BigDecimal? = null,

    @SerializedName("close_price")
    private val _close: BigDecimal? = null,

    @SerializedName("ticker_volume")
    private val _volume: BigDecimal? = null

) : Serializable {
    fun price() = formatPriceNumberDisplay(_price)
    fun priceChangeDisplayable() =
        "${formatPriceNumberDisplay(priceChange)} (${formatPriceNumberDisplay(_percentageChange)})"

    fun open() = formatPriceNumberDisplay(_open).padStart(12, ' ')
    fun high() = formatPriceNumberDisplay(_high).padStart(12, ' ')
    fun low() = formatPriceNumberDisplay(_low).padStart(12, ' ')
    fun volume() = formatPriceNumberNoDecimal(_volume).padStart(12, ' ')

}