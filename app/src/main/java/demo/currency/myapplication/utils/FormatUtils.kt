package demo.currency.myapplication.utils

import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.NumberFormat

object FormatUtils {

    fun formatPriceNumberDisplay(num: BigDecimal?): String {
        return num?.let {
            DecimalFormat("###,###.###").format(num)
        } ?: ""
    }

    fun formatPriceNumberNoDecimal(num: BigDecimal?): String {
        return num?.let {
            DecimalFormat("###,###").format(num)
        } ?: ""
    }

    fun formatStringNumber2dp(num: String): String {
        return if (num.isNotBlank()) {
            val number = NumberFormat.getInstance().parse(num)
            DecimalFormat("###,###.##").apply { roundingMode = RoundingMode.DOWN }.format(number)
        } else {
            ""
        }
    }
}