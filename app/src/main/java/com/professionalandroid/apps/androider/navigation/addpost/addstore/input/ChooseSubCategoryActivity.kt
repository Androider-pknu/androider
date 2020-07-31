package com.professionalandroid.apps.androider.navigation.addpost.addstore.input

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.professionalandroid.apps.androider.R
import kotlinx.android.synthetic.main.activity_choosesubcategory.*
import kotlinx.android.synthetic.main.item_category.view.*

class ChooseSubCategoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choosesubcategory)

        btn_choosesubcategory_back.setOnClickListener {
            onBackPressed()
        }

        inflateContent()
    }

    private fun inflateContent() {
        val inflater = LayoutInflater.from(this)
        val intent = intent
        val prefix = when (intent.getStringExtra("subTitle")) {
            "store" -> "sub_store_category"
            "extra" -> "sub_extra_category"
            "item" -> "item_sub_category"
            else -> ""
        }

        textview_choosesubcategory_supercategory.text = intent.getStringExtra("name")
        val field =
            R.array::class.java.getField("${prefix}_${intent.getIntExtra("index", -1)}")
        val array = resources.getStringArray(field.getInt(null))

        for (subCategory in array) {
            val view = inflater.inflate(
                R.layout.item_category,
                layout_choosesubcategory_subcategory,
                false
            )
            view.textview_itemcategory_category.text = subCategory
            view.textview_itemcategory_category.setOnClickListener {
                returnSubCategory(subCategory)
            }
            layout_choosesubcategory_subcategory.addView(view)
        }
    }

    private fun returnSubCategory(subCategory: String) {
        val result = Intent()
        result.putExtra("subCategory", subCategory)
        setResult(Activity.RESULT_OK, result)
        finish()
    }
}
