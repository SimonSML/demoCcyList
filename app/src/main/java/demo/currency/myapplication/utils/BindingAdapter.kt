package demo.currency.myapplication.utils

import android.graphics.Color
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import demo.currency.myapplication.R
import java.math.BigDecimal


@BindingAdapter("priceColor")
fun setPriceColor(view: TextView, number: BigDecimal?) {
    number?.let {
        val color = when (it.signum()) {
            1 -> Color.GREEN
            -1 -> Color.RED
            else -> Color.BLACK
        }
        if (view.currentTextColor != color) {
            view.setTextColor(color)
        }
    }
}

@BindingAdapter("priceColor")
fun setPriceColor(view: ImageView, number: BigDecimal?) {
    number?.let {
        val (colorRes, visibility) = when (it.signum()) {
            1 -> Pair(R.color.stock_positive, View.VISIBLE)
            -1 -> Pair(R.color.stock_negative, View.VISIBLE)
            else -> Pair(R.color.black, View.GONE)
        }
        if (view.visibility != visibility) {
            view.visibility = visibility
        }
        view.setColorFilter(view.context.resources.getColor(colorRes))
    }
}