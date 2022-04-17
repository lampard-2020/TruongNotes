package com.notes.miniapp.main.base

import androidx.appcompat.app.AppCompatActivity
import com.notes.miniapp.utils.LoadingDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
open class BaseActivity : AppCompatActivity() {

    private var loadingDialog: LoadingDialog? = null

    fun showLoading(show: Boolean) {
        if (loadingDialog == null) {
            loadingDialog = LoadingDialog(this)
        }
        if (show) {
            loadingDialog!!.show()
        } else {
            if (loadingDialog!!.isShowing) loadingDialog!!.dismiss()
        }
    }
}
