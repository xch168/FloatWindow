package io.github.xch168.floatwindow.rom

import android.Manifest
import android.content.Context
import android.content.Intent

import android.os.Build

/**
 * Created by Kemp Xu on 2021/10/3.
 */
object SmartisanRom : BaseRom() {

    override fun toApplyFloatPermission(context: Context): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return false
        }
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // Smartisan|5.1.1|2.5.3
            val intent = Intent("com.smartisanos.security.action.SWITCHED_PERMISSIONS_NEW")
            intent.setClassName(
                "com.smartisanos.security",
                "com.smartisanos.security.SwitchedPermissions"
            )
            intent.putExtra("index", 17)
            startSafely(context, intent)
        } else {
            // Smartisan|4.4.4|2.1.2
            val intent = Intent("com.smartisanos.security.action.SWITCHED_PERMISSIONS")
            intent.setClassName(
                "com.smartisanos.security",
                "com.smartisanos.security.SwitchedPermissions"
            )
            intent.putExtra("permission", arrayOf(Manifest.permission.SYSTEM_ALERT_WINDOW))
            startSafely(context, intent)
        }
    }
}