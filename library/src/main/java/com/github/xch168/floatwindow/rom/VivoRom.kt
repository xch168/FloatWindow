package com.github.xch168.floatwindow.rom

import android.content.Context
import android.content.Intent

/**
 * Created by Kemp Xu on 2021/10/3.
 */
object VivoRom : BaseRom() {

    override fun toApplyFloatPermission(context: Context): Boolean {
        // Do not support directly to the floating window setting page,
        // only go to the permission setting page
        var intent = Intent("com.vivo.permissionmanager")
        intent.setClassName(
            "com.vivo.permissionmanager",
            "com.vivo.permissionmanager.activity.PurviewTabActivity"
        )
        if (startSafely(context, intent)) {
            return true
        }
        // Can only go to the homepage of i Butler
        intent = Intent("com.iqoo.secure")
        intent.setClassName("com.iqoo.secure", "com.iqoo.secure.MainActivity")
        return startSafely(context, intent)
    }
}