package com.professionalandroid.apps.androider.navigation.addpost.additem.input

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import com.professionalandroid.apps.androider.R
import com.professionalandroid.apps.androider.navigation.addpost.addstore.input.ChooseSubCategoryActivity
import kotlinx.android.synthetic.main.activity_chooseitemcategory.*
import kotlinx.android.synthetic.main.item_category.view.*

class
ChooseItemCategoryActivity : AppCompatActivity() {
    val ITEM_CATEGORY_REQUEST_CODE = 5003

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chooseitemcategory)

        btn_chooseitemcategory_back.setOnClickListener {
            onBackPressed()
        }

        inflateContent()
    }

    private fun inflateContent() {
        val itemList = resources.getStringArray(R.array.item_category)
        val inflater = LayoutInflater.from(this)
        val itemLayout = layout_chooseitemcategory_items

        for ((index, item) in itemList.withIndex()) {
            val view = inflater.inflate(R.layout.item_category, itemLayout, false)
            view.textview_itemcategory_category.text = item
            view.textview_itemcategory_category.setOnClickListener {
                startSubCategory("item", item, index)
            }
            itemLayout.addView(view)
        }
    }

    private fun startSubCategory(subTitle: String, item: String?, index: Int) {
        val intent = Intent(this, ChooseSubCategoryActivity::class.java).apply {
            putExtra("subTitle", subTitle)
            putExtra("name", item)
            putExtra("index", index)
        }
        startActivityForResult(intent, ITEM_CATEGORY_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == ITEM_CATEGORY_REQUEST_CODE) {
            when (resultCode) {
                Activity.RESULT_OK -> {
                    val subCategory = data?.getStringExtra("subCategory")
                    val result = Intent()
                    result.putExtra("item", subCategory)
                    setResult(Activity.RESULT_OK, result)
                    finish()
                }
                else ->
                    Toast.makeText(this, "fail", Toast.LENGTH_LONG).show()
            }
        }
    }
}