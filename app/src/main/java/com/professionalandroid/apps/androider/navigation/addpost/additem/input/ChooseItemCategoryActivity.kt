package com.professionalandroid.apps.androider.navigation.addpost.additem.input

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.professionalandroid.apps.androider.R
import kotlinx.android.synthetic.main.activity_chooseitemcategory.*

class
ChooseItemCategoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chooseitemcategory)

        btn_chooseitemcategory_back.setOnClickListener {
            onBackPressed()
        }
    }
}