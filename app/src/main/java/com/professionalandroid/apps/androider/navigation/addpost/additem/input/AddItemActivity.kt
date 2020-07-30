package com.professionalandroid.apps.androider.navigation.addpost.additem.input

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.professionalandroid.apps.androider.R
import com.professionalandroid.apps.androider.navigation.addpost.addstore.input.ChooseCategoryActivity
import kotlinx.android.synthetic.main.activity_additem.*
import kotlinx.android.synthetic.main.activity_addstore.*
import kotlinx.android.synthetic.main.activity_choosesubcategory.*

class AddItemActivity : AppCompatActivity() {
    val ITEM_CATEGORY_REQUEST_CODE = 5003

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_additem)

        btn_additem_back.setOnClickListener {
            onBackPressed()
        }

        layout_additem_category.setOnClickListener {
            startActivityForResult(
                Intent(this, ChooseItemCategoryActivity::class.java),
                ITEM_CATEGORY_REQUEST_CODE
            )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == ITEM_CATEGORY_REQUEST_CODE) {
            when (resultCode) {
                Activity.RESULT_OK -> {
                    val item = data?.getStringExtra("item")
                    textview_additem_selecteditem.text = item
                }
                else -> Toast.makeText(this, "fail", Toast.LENGTH_LONG).show()
            }
        }
    }
}