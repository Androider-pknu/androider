package com.professionalandroid.apps.androider.util

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.Window
import com.professionalandroid.apps.androider.R

object Settings {

    // 상태바 true#투명 false#원래대로
    @SuppressLint("ResourceAsColor")
    fun setWindowTranslucent(window: Window?, on: Boolean) {
        val win = window ?: return
        when (on) {
            true -> {
                win.decorView.systemUiVisibility =
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    win.statusBarColor = Color.TRANSPARENT
                }
            }
            false -> {
                win.decorView.systemUiVisibility =
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    win.statusBarColor = R.color.colorPrimary
                }
            }
        }
    }
}