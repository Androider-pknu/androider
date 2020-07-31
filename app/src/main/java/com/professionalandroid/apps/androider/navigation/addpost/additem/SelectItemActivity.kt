package com.professionalandroid.apps.androider.navigation.addpost.additem

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.professionalandroid.apps.androider.R
import com.professionalandroid.apps.androider.navigation.addpost.additem.input.AddItemActivity
import kotlinx.android.synthetic.main.activity_selectitem.*

class SelectItemActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selectitem)

        btn_selectitem_back.setOnClickListener {
            onBackPressed()
        }

        btn_selectitem_additem.setOnClickListener {
            startActivity(Intent(this, AddItemActivity::class.java))
        }
    }
}