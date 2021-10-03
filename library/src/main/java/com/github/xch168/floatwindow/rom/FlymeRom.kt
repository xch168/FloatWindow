package com.github.xch168.floatwindow.rom

import android.content.Context
import android.content.Intent

/**
 * Created by Kemp Xu on 2021/10/3.
 */
object FlymeRom : BaseRom() {

    override fun toApplyFloatPermission(context: Context): Boolean {
        val intent = Intent("com.meizu.safe.security.SHOW_APPSEC")
        intent.setClassName("com.meizu.safe", "com.meizu.safe.security.AppSecActivity")
        intent.putExtra("packageName", context.packageName)
        return startSafely(context, intent)
    }
}