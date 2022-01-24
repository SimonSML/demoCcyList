package demo.currency.myapplication

import android.os.SystemClock
import android.view.View
import demo.currency.myapplication.utils.SafeClickListener
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.verify
import org.junit.Before
import org.junit.Test

class SafeClickListenerTest {
    private val onclickListener: View.OnClickListener = mockk(relaxed = true)

    @Before
    fun setup() {
        mockkStatic(SystemClock::class)
        every { SystemClock.elapsedRealtime() } returns 1000
    }

    @Test
    fun testOnClick() {
        val safeClickListener = SafeClickListener(onclickListener)
        safeClickListener.onClick(null)
        verify(exactly = 1) { onclickListener.onClick(null) }
    }

    @Test
    fun testOnClickLessThanThreshold() {
        every { SystemClock.elapsedRealtime() } returns 499
        val safeClickListener = SafeClickListener(onclickListener)
        safeClickListener.onClick(null)
        safeClickListener.onClick(null)
        safeClickListener.onClick(null)
        safeClickListener.onClick(null)
        verify(exactly = 0) { onclickListener.onClick(null) }
    }

    @Test
    fun testContinuouslyClicked() {
        every { SystemClock.elapsedRealtime() } returns 501
        val safeClickListener = SafeClickListener(onclickListener)
        safeClickListener.onClick(null)
        verify(exactly = 1) { onclickListener.onClick(null) }
        every { SystemClock.elapsedRealtime() } returns 999
        safeClickListener.onClick(null)
        safeClickListener.onClick(null)
        safeClickListener.onClick(null)
        verify(exactly = 1) { onclickListener.onClick(null) }
        every { SystemClock.elapsedRealtime() } returns 1200
        safeClickListener.onClick(null)
        safeClickListener.onClick(null)
        safeClickListener.onClick(null)
        verify(exactly = 2) { onclickListener.onClick(null) }
    }
}