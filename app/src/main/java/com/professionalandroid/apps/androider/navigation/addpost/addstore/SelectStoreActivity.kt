package com.professionalandroid.apps.androider.navigation.addpost.addstore

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.professionalandroid.apps.androider.R
import com.professionalandroid.apps.androider.navigation.addpost.addstore.addressing.ChooseAddressingDialog
import kotlinx.android.synthetic.main.activity_selectstore.*

class SelectStoreActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selectstore)
        btn_selectstore_addstore.setOnClickListener {
            val dialog =
                ChooseAddressingDialog()
            dialog.show(supportFragmentManager, "missiles")
        }
    }
}
