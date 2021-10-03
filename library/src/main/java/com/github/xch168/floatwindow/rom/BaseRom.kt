package com.github.xch168.floatwindow.rom

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import com.github.xch168.floatwindow.FLog
import java.lang.Exception

/**
 * Created by Kemp Xu on 2021/10/3.
 */
open class BaseRom {

    open fun toApplyFloatPermission(context: Context): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
            intent.data = Uri.parse("package:" + context.packageName)
            context.startActivity(intent)
            return true
        }
        return false
    }

    fun startSafely(context: Context, intent: Intent): Boolean {
        if (intent.resolveActivity(context.packageManager) != null) {
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            try {
                context.startActivity(intent)
                return true
            } catch (e: Exception) {
                FLog.e(e.toString())
            }
        }
        FLog.e("Intent is not available : $intent")
        return false
    }
}