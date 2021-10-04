package io.github.xch168.floatwindow.rom

import android.content.Context
import android.content.Intent

/**
 * Created by Kemp Xu on 2021/10/3.
 */
object OppoRom : BaseRom() {

    override fun toApplyFloatPermission(context: Context): Boolean {
        val intent = Intent()
        intent.putExtra("packageName", context.packageName)
        // OPPO A53|5.1.1|2.1
        intent.action = "com.oppo.safe"
        intent.setClassName(
            "com.oppo.safe",
            "com.oppo.safe.permission.floatwindow.FloatWindowListActivity"
        )
        if (startSafely(context, intent)) {
            return true
        }
        // OPPO R7s|4.4.4|2.1
        intent.action = "com.color.safecenter"
        intent.setClassName(
            "com.color.safecenter",
            "com.color.safecenter.permission.floatwindow.FloatWindowListActivity"
        )
        if (startSafely(context, intent)) {
            return true
        }
        intent.action = "com.coloros.safecenter"
        intent.setClassName(
            "com.coloros.safecenter",
            "com.coloros.safecenter.sysfloatwindow.FloatWindowListActivity"
        )
        return startSafely(context, intent)
    }
}