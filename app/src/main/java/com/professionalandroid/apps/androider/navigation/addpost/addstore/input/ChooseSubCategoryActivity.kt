package com.professionalandroid.apps.androider.navigation.addpost.addstore.input

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.professionalandroid.apps.androider.R
import kotlinx.android.synthetic.main.activity_choosesubcategory.*

class ChooseSubCategoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choosesubcategory)

        btn_choosesubcategory_back.setOnClickListener {
            onBackPressed()
        }
    }
}
