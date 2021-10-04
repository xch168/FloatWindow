package io.github.xch168.floatwindow.rom

import android.os.Build

import android.text.TextUtils
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.util.*

/**
 * Created by Kemp Xu on 2021/10/3.
 */
object RomUtil {
    const val ROM_MIUI = "MIUI"
    const val ROM_EMUI = "EMUI"
    const val ROM_FLYME = "FLYME"
    const val ROM_OPPO = "OPPO"
    const val ROM_VIVO = "VIVO"
    const val ROM_SMARTISAN = "SMARTISAN"

    private const val KEY_NAME_MIUI = "Xiaomi"
    private const val KEY_NAME_EMUI = "HUAWEI"
    private const val KEY_NAME_FLYME = "Meizu"
    private const val KEY_NAME_OPPO = "OPPO"
    private const val KEY_NAME_VIVO = "vivo"

    private const val KEY_VERSION_MIUI = "ro.miui.ui.version.name"
    private const val KEY_VERSION_EMUI = "ro.build.version.emui"
    private const val KEY_VERSION_OPPO = "ro.build.version.opporom"
    private const val KEY_VERSION_VIVO = "ro.vivo.os.version"
    private const val KEY_VERSION_SMARTISAN = "ro.smartisan.version"

    private var romName: String? = null

    fun isMiui(): Boolean {
        return check(ROM_MIUI)
    }

    fun isEmui(): Boolean {
        return check(ROM_EMUI)
    }

    fun isFlyme(): Boolean {
        return check(ROM_FLYME)
    }

    fun isOppo(): Boolean {
        return check(ROM_OPPO)
    }

    fun isVivo(): Boolean {
        return check(ROM_VIVO)
    }

    fun isSmartisan(): Boolean {
        return check(ROM_SMARTISAN)
    }

    private fun check(rom: String): Boolean {
        if (romName != null) {
            return romName == rom
        }
        romName = if (Build.MANUFACTURER == KEY_NAME_MIUI) {
            ROM_MIUI
        } else if (Build.MANUFACTURER == KEY_NAME_EMUI) {
            ROM_EMUI
        } else if (Build.MANUFACTURER == KEY_NAME_FLYME || Build.DISPLAY.uppercase(Locale.getDefault()).contains(
                ROM_FLYME
            )) {
            ROM_FLYME
        } else if (Build.MANUFACTURER == KEY_NAME_OPPO) {
            ROM_OPPO
        } else if (Build.MANUFACTURER == KEY_NAME_VIVO) {
            ROM_VIVO
        } else if (!TextUtils.isEmpty(getProp(KEY_VERSION_MIUI))) {
            ROM_MIUI
        } else if (!TextUtils.isEmpty(getProp(KEY_VERSION_EMUI))) {
            ROM_EMUI
        } else if (!TextUtils.isEmpty(getProp(KEY_VERSION_OPPO))) {
            ROM_OPPO
        } else if (!TextUtils.isEmpty(getProp(KEY_VERSION_VIVO))) {
            ROM_VIVO
        } else if (!TextUtils.isEmpty(getProp(KEY_VERSION_SMARTISAN))) {
            ROM_SMARTISAN
        } else {
            Build.MANUFACTURER.uppercase(Locale.getDefault())
        }
        return romName == rom
    }

    private fun getProp(name: String): String? {
        val line: String?
        var input: BufferedReader? = null
        try {
            val p = Runtime.getRuntime().exec("getprop $name")
            input = BufferedReader(InputStreamReader(p.inputStream), 1024)
            line = input.readLine()
            input.close()
        } catch (e: IOException) {
            return null
        } finally {
            if (input != null) {
                try {
                    input.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
        return line
    }
}