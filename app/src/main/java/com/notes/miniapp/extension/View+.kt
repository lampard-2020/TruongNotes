package com.notes.miniapp.extension

import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.notes.miniapp.utils.FragmentViewBindingDelegate

private var lastClickedTime = 0L
fun View.setSafeClickListener(onViewClick: () -> Unit) {
    this.setOnClickListener {
        if (SystemClock.elapsedRealtime() - lastClickedTime > 1000) {
            lastClickedTime = SystemClock.elapsedRealtime()
            onViewClick.invoke()
        }
    }
}

inline fun <T : ViewBinding> AppCompatActivity.viewBinding(
    crossinline bindingInflater: (LayoutInflater) -> T
) =
    lazy(LazyThreadSafetyMode.NONE) {
        bindingInflater.invoke(layoutInflater)
    }

fun <T : ViewBinding> Fragment.viewBinding(viewBindingFactory: (View) -> T) =
    FragmentViewBindingDelegate(this, viewBindingFactory)

fun View.visible(isVisible: Boolean = true) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.click(clickListener: View.OnClickListener) {
    setOnClickListener(clickListener)
}
