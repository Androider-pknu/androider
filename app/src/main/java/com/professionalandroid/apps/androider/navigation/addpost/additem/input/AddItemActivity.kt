package com.professionalandroid.apps.androider.navigation.addpost.additem.input

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.professionalandroid.apps.androider.R
import kotlinx.android.synthetic.main.activity_additem.*

class AddItemActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_additem)

        btn_additem_back.setOnClickListener {
            onBackPressed()
        }

        layout_additem_category.setOnClickListener {
            startActivity(Intent(this, ChooseItemCategoryActivity::class.java))
        }
    }
}