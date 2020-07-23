package com.professionalandroid.apps.androider.navigation.addpost.addstore.input

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.professionalandroid.apps.androider.R
import kotlinx.android.synthetic.main.activity_addstore.*

class AddStoreActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addstore)

        layout_addstore_category.setOnClickListener {
            startActivity(Intent(this, ChooseCategoryActivity::class.java))
        }

        btn_addstore_back.setOnClickListener {
            onBackPressed()
        }
    }
}
