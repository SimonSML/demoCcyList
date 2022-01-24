package demo.currency.myapplication.utils

import android.os.SystemClock
import android.util.Log
import android.view.View

class SafeClickListener(private val clickListener: View.OnClickListener) : View.OnClickListener {
    private var lastClickTime: Long = 0
    override fun onClick(v: View?) {
        val current = SystemClock.elapsedRealtime()
        if (current - lastClickTime > CLICK_THRESHOLD) {
            lastClickTime = current
            clickListener.onClick(v)
        } else {
            Log.d("SafeClickListener", "Click too fast, time diff = ${current - lastClickTime} ms")
        }
    }

    companion object {
        private const val CLICK_THRESHOLD = 500L
    }
}