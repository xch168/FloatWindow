package com.github.xch168.floatwindow.rom

import android.content.Context
import android.content.Intent
import android.net.Uri

import android.os.Build
import android.provider.Settings

/**
 * Created by Kemp Xu on 2021/10/3.
 */
object MiuiRom : BaseRom() {

    override fun toApplyFloatPermission(context: Context): Boolean {
        val intent = Intent("miui.intent.action.APP_PERM_EDITOR")
        intent.putExtra("extra_pkgname", context.packageName)
        intent.setClassName(
            "com.miui.securitycenter",
            "com.miui.permcenter.permissions.AppPermissionsEditorActivity"
        )
        if (startSafely(context, intent)) {
            return true
        }
        intent.setClassName(
            "com.miui.securitycenter",
            "com.miui.permcenter.permissions.PermissionsEditorActivity"
        )
        if (startSafely(context, intent)) {
            return true
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            val intent1 = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            intent1.data = Uri.fromParts("package", context.packageName, null)
            return startSafely(context, intent1)
        }
        return false
    }
}