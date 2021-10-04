package io.github.xch168.floatwindow.demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import io.github.xch168.floatwindow.FloatPermissionHelper
import io.github.xch168.floatwindow.FloatWindow

class MainActivity : AppCompatActivity() {
    private var floatWindow: FloatWindow? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun showFloatWindow(view: android.view.View) {
        if (!FloatPermissionHelper.checkPermission(this)) {
            FloatPermissionHelper.toApplyPermission(this)
            return
        }
        if (floatWindow == null) {
            val floatView = LayoutInflater.from(this).inflate(R.layout.float_view, null)

            floatWindow = FloatWindow.with(this)
                .view(floatView)
                .autoAlign(true)
                .create()
        }
        floatWindow?.show()
    }

    fun hideFloatWindow(view: android.view.View) {
        floatWindow?.hide()
    }
}