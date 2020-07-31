package com.professionalandroid.apps.androider.navigation.addpost.insertdata

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.professionalandroid.apps.androider.R
import kotlinx.android.synthetic.main.activity_additem.*

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