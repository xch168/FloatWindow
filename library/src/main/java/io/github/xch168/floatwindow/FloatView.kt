package io.github.xch168.floatwindow

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import android.view.animation.BounceInterpolator
import android.widget.FrameLayout
import kotlin.math.abs

/**
 * Created by Kemp Xu on 2021/10/2.
 */
@SuppressLint("ViewConstructor")
class FloatView(context: Context, contentView: View) : FrameLayout(context) {
    private val touchSlop = ViewConfiguration.get(context).scaledTouchSlop
    private val statusBarHeight = getStatusBarHeight()

    private var rawX = 0f
    private var rawY = 0f
    private var downX = 0f
    private var downY = 0f

    private var interceptX = 0f
    private var interceptY = 0f

    private var autoAlign: Boolean = false


    private var onLocationUpdateListener: OnLocationUpdateListener? = null

    init {
        addView(contentView)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        var isIntercept = false
        when (ev?.action) {
            MotionEvent.ACTION_DOWN -> {
                interceptX = ev.x
                interceptY = ev.y
                downX = ev.x
                downY = ev.y
                isIntercept = false
            }
            MotionEvent.ACTION_MOVE -> {
                isIntercept = abs(ev.x - interceptX) > touchSlop && abs(ev.y - interceptY) > touchSlop
            }
            MotionEvent.ACTION_UP -> {
                //
            }
            else -> {
                //
            }
        }
        return isIntercept
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event?.let {
            rawX = it.rawX
            rawY = it.rawY - statusBarHeight

            when (it.action) {
                MotionEvent.ACTION_DOWN -> actionDown(it)
                MotionEvent.ACTION_MOVE -> actionMove(it)
                MotionEvent.ACTION_UP -> actionUp(it)
                MotionEvent.ACTION_OUTSIDE -> actionOutSide(it)
                else -> {
                    //
                }
            }
        }
        return false
    }

    private fun actionDown(event: MotionEvent) {

    }

    private fun actionMove(event: MotionEvent) {
        onLocationUpdateListener?.let {
            it.onLocationUpdate((rawX - downX).toInt(), (rawY - downY).toInt())
        }
    }

    private fun actionUp(event: MotionEvent) {
        if (autoAlign) {
            autoAlign()
        }
    }

    private fun actionOutSide(event: MotionEvent) {

    }

    private fun autoAlign() {
        val fromX = rawX - downX
        val toX = if (rawX <= getScreenWidth() / 2) {
            0
        } else {
            getScreenWidth()
        }
        FLog.i("fromX: $fromX ==> toX: $toX")
        ValueAnimator.ofFloat(fromX, toX.toFloat()).apply {
            duration = 500
            interpolator = BounceInterpolator()
            addUpdateListener {
                val curX = animatedValue as Float
                onLocationUpdateListener?.let {
                    it.onLocationUpdate(curX.toInt(), (rawY - downY).toInt())
                }
            }
            start()
        }
    }

    private fun getStatusBarHeight(): Int {
        val identifier: Int = Resources.getSystem().getIdentifier(
            "status_bar_height",
            "dimen", "android"
        )
        return if (identifier > 0) {
            Resources.getSystem().getDimensionPixelSize(identifier)
        } else 0
    }

    private fun getScreenWidth(): Int {
        return Resources.getSystem().displayMetrics.widthPixels
    }

    fun setAutoAlign(autoAlign: Boolean) {
        this.autoAlign = autoAlign
    }

    fun setOnLocationUpdateListener(listener: OnLocationUpdateListener) {
        onLocationUpdateListener = listener
    }

    interface OnLocationUpdateListener {
        fun onLocationUpdate(x: Int, y: Int)
    }

}