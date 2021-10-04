package io.github.xch168.floatwindow

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.PixelFormat
import android.os.Build
import android.view.View
import android.view.WindowManager
import android.view.Gravity

/**
 * Created by Kemp Xu on 2021/10/2.
 */
class FloatWindow private constructor(builder: Builder) : FloatView.OnLocationUpdateListener {
    private var mWindowManager: WindowManager? = null
    private var mLayoutParams: WindowManager.LayoutParams? = null

    private val context = builder.context
    private val contentView = builder.contentView
    private val startX = builder.startX
    private val startY = builder.startY
    private val moveAble = builder.moveAble
    private val autoAlign = builder.autoAlign

    private lateinit var floatView: FloatView

    private var isFloatViewAdded = false

    init {
        initWindowManager()
        initFloatView()
    }

    private fun initWindowManager() {
        mWindowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager?
        mLayoutParams = WindowManager.LayoutParams().apply {
            type = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
            } else {
                WindowManager.LayoutParams.TYPE_SYSTEM_ALERT
            }

            flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
            format = PixelFormat.RGBA_8888

            width = WindowManager.LayoutParams.WRAP_CONTENT
            height = WindowManager.LayoutParams.WRAP_CONTENT

            gravity = Gravity.START or Gravity.TOP
            x = startX
            y = startY
        }
    }


    @SuppressLint("ClickableViewAccessibility")
    private fun initFloatView() {
        floatView = FloatView(context, contentView)
        floatView.setAutoAlign(autoAlign)
        if (moveAble) {
            floatView.setOnLocationUpdateListener(this)
        }
    }

    override fun onLocationUpdate(x: Int, y: Int) {
        mLayoutParams?.let {
            it.x = x
            it.y = y
        }
        mWindowManager?.updateViewLayout(floatView, mLayoutParams)
    }

    fun show() {
        floatView.visibility = View.VISIBLE
        if (!isFloatViewAdded) {
            mWindowManager?.addView(floatView, mLayoutParams)
            isFloatViewAdded = true
        }
    }

    fun hide() {
        floatView.visibility = View.GONE

    }

    class Builder(val context: Context) {
        lateinit var contentView: View
        var startX = 0
        var startY = 0
        var moveAble = true
        var autoAlign = false

        fun view(view: View): Builder {
            this.contentView = view
            return this
        }

        fun startX(x: Int): Builder {
            this.startX = x
            return this
        }

        fun startY(y: Int): Builder {
            this.startY = y
            return this
        }

        fun moveAble(moveAble: Boolean): Builder {
            this.moveAble = moveAble
            return this
        }

        fun autoAlign(autoAlign: Boolean): Builder {
            this.autoAlign = autoAlign
            return this
        }

        fun create(): FloatWindow {
            return FloatWindow(this)
        }
    }

    companion object {

        @JvmStatic
        fun with(context: Context): Builder {
            return Builder(context)
        }
    }

}