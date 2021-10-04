package io.github.xch168.floatwindow.rom

import android.content.Context
import android.os.Build

import android.content.Intent

/**
 * Created by Kemp Xu on 2021/10/3.
 */
object EmuiRom : BaseRom() {

    override fun toApplyFloatPermission(context: Context): Boolean {
        val intent = Intent()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            intent.setClassName(
                "com.huawei.systemmanager",
                "com.huawei.systemmanager.addviewmonitor.AddViewMonitorActivity"
            )
            if (startSafely(context, intent)) {
                return true
            }
        }
        // Huawei Honor P6|4.4.4|3.0
        intent.setClassName(
            "com.huawei.systemmanager",
            "com.huawei.notificationmanager.ui.NotificationManagmentActivity"
        )
        intent.putExtra("showTabsNumber", 1)
        if (startSafely(context, intent)) {
            return true
        }
        intent.setClassName(
            "com.huawei.systemmanager",
            "com.huawei.permissionmanager.ui.MainActivity"
        )
        return startSafely(context, intent)
    }
}