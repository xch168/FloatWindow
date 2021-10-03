package com.github.xch168.floatwindow

import android.annotation.SuppressLint
import android.app.AppOpsManager
import android.content.Context
import android.os.Binder
import android.os.Build
import android.provider.Settings
import androidx.annotation.RequiresApi
import com.github.xch168.floatwindow.rom.*

/**
 * Created by Kemp Xu on 2021/10/2.
 */
object FloatPermissionHelper {
    private const val OP_SYSTEM_ALERT_WINDOW = 24

    fun checkPermission(context: Context): Boolean {
        return when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> {
                Settings.canDrawOverlays(context)
            }
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT -> {
                checkOp(context)
            }
            else -> true
        }
    }

    @SuppressLint("DiscouragedPrivateApi")
    @RequiresApi(Build.VERSION_CODES.KITKAT)
    private fun checkOp(context: Context): Boolean {
        val appOpsMgr = context.getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager? ?: return false
        try {
            val method = AppOpsManager::class.java.getDeclaredMethod(
                "checkOp",
                Int::class.javaPrimitiveType,
                Int::class.javaPrimitiveType,
                String::class.java
            )
            return AppOpsManager.MODE_ALLOWED == method.invoke(
                appOpsMgr,
                OP_SYSTEM_ALERT_WINDOW,
                Binder.getCallingUid(),
                context.packageName
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }

    fun toApplyPermission(context: Context): Boolean {
        return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            val rom = when {
                RomUtil.isMiui() -> MiuiRom
                RomUtil.isEmui() -> EmuiRom
                RomUtil.isFlyme() -> FlymeRom
                RomUtil.isOppo() -> OppoRom
                RomUtil.isVivo() -> VivoRom
                else -> null
            }
            rom?.toApplyFloatPermission(context) ?: false
        } else {
            BaseRom().toApplyFloatPermission(context)
        }
    }
}