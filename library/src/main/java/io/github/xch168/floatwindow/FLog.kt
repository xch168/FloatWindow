package io.github.xch168.floatwindow

import android.util.Log

/**
 * Created by Kemp Xu on 2021/10/2.
 */
object FLog {
    private const val TAG = "FloatWindow"

    fun d(message: String) {
        Log.d(TAG, message)
    }

    fun i(message: String) {
        Log.i(TAG, message)
    }

    fun e(message: String) {
        Log.e(TAG, message)
    }

}