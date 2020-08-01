package com.professionalandroid.apps.androider.navigation.addpost.insertdata

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.professionalandroid.apps.androider.R
import kotlinx.android.synthetic.main.activity_additem.*

class AddItemActivity : AppCompatActivity() {
    val ITEM_CATEGORY_REQUEST = 5002

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_additem)

        btn_additem_back.setOnClickListener {
            onBackPressed()
        }

        layout_additem_category.setOnClickListener {
            val intent = Intent(this, ChooseCategoryActivity::class.java)
            intent.putExtra("type", arrayOf("item"))
            startActivityForResult(intent, ITEM_CATEGORY_REQUEST)
        }

        btn_additem_complete.setOnClickListener {
            // TODO
            // make item data model
            // insert data model to database
            // return data model to add post activity
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == ITEM_CATEGORY_REQUEST) {
            when (resultCode) {
                Activity.RESULT_OK -> {
                    val category = data?.getStringExtra("subCategory")
                    textview_additem_selecteditem.text = category
                }
                else -> Toast.makeText(this, "fail", Toast.LENGTH_LONG).show()
            }
        }
    }
}