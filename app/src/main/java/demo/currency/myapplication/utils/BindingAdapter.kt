package demo.currency.myapplication.utils

import android.graphics.Color
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
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
        val (color, visibility) = when (it.signum()) {
            1 -> Pair(Color.GREEN, View.VISIBLE)
            -1 -> Pair(Color.RED, View.VISIBLE)
            else -> Pair(Color.BLACK, View.GONE)
        }
        if (view.visibility != visibility) {
            view.visibility = visibility
        }
        view.setColorFilter(color)
    }
}