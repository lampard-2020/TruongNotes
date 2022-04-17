package com.notes.miniapp.utils

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.os.Environment
import android.os.storage.StorageManager
import android.util.Log
import androidx.annotation.RequiresApi
import java.lang.reflect.Method

object LogUtils {

    private const val TAG = "MINI_APP"

    fun d(message: String) {
        val ste = Throwable().stackTrace
        val text = "[" + ste[1].fileName + ":" + ste[1].lineNumber + ":" + ste[1].methodName + "()]"
        Log.d(TAG, text + message)
    }

    fun i(message: String) {
        val ste = Throwable().stackTrace
        val text = "[" + ste[1].fileName + ":" + ste[1].lineNumber + ":" + ste[1].methodName + "()]"
        Log.i(TAG, text + message)
    }

    fun e(message: String) {
        val ste = Throwable().stackTrace
        val text =
            "[" + ste[1].fileName + ":" + ste[1].lineNumber + ":" + ste[1].methodName + "()] !!!WARNING "
        Log.e(TAG, text + message)
    }


}