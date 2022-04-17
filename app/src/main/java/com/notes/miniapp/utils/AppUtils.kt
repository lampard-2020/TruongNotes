package com.notes.miniapp.utils

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.notes.miniapp.R

object AppUtils {
    fun showAlertDialog(
        context: Context,
        message: String,
        doYes: () -> Unit,
        doNo: () -> Unit = {}
    ) {
        val alertDialog = AlertDialog.Builder(context)

        alertDialog.apply {
            setMessage(message)
            setCancelable(false)
            setPositiveButton(context.getString(R.string.str_yes)) { _, _ -> doYes.invoke() }
            setNegativeButton(context.getString(R.string.str_no)) { _, _ -> doNo.invoke() }
            show()
        }
    }
}
