package com.professionalandroid.apps.androider.navigation.addpost

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.professionalandroid.apps.androider.R
import kotlinx.android.synthetic.main.activity_selectstore.*

class SelectStoreActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selectstore)
        btn_selectstore_addstore.setOnClickListener {
            dialog()
        }
    }

    private fun dialog() {
        val dialog = ChooseAddressingDialog()
        dialog.show(supportFragmentManager, "missiles")
    }
}
