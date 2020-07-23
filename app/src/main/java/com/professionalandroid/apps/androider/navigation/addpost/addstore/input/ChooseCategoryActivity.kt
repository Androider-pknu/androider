package com.professionalandroid.apps.androider.navigation.addpost.addstore.input

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.professionalandroid.apps.androider.R
import kotlinx.android.synthetic.main.activity_choosecategory.*

class ChooseCategoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choosecategory)

        btn_choosecategory_back.setOnClickListener {
            onBackPressed()
        }
    }
}
