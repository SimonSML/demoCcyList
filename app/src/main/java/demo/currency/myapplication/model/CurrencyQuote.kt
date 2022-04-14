package demo.currency.myapplication.model

import demo.currency.myapplication.utils.FormatUtils.formatPriceNumberDisplay
import demo.currency.myapplication.utils.FormatUtils.formatPriceNumberNoDecimal
import java.io.Serializable
import java.math.BigDecimal

/**
 * Created on 13/4/2022.
 */
data class CurrencyQuote(
    val exchange: String? = null,
    val currency: String? = null,
    val ticker: String? = null,
    val name: String? = null,
    private val _price: BigDecimal? = null,
    val priceChange: BigDecimal? = null,
    private val _percentageChange: BigDecimal? = null,
    private val _open: BigDecimal? = null,
    private val _high: BigDecimal? = null,
    private val _low: BigDecimal? = null,
    private val _close: BigDecimal? = null,
    private val _volume: BigDecimal? = null
) : Serializable {
    val price = formatPriceNumberDisplay(_price)
    val priceChangeDisplayable =
        "${formatPriceNumberDisplay(priceChange)} (${formatPriceNumberDisplay(_percentageChange)})"
    val open = formatPriceNumberDisplay(_open).padStart(12, ' ')
    val high = formatPriceNumberDisplay(_high).padStart(12, ' ')
    val low = formatPriceNumberDisplay(_low).padStart(12, ' ')
    val volume = formatPriceNumberNoDecimal(_volume).padStart(12, ' ')

}