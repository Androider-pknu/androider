package com.professionalandroid.apps.androider.navigation.addpost.insertdata

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.professionalandroid.apps.androider.R
import kotlinx.android.synthetic.main.activity_choosecategory.*
import kotlinx.android.synthetic.main.item_category.view.*

class ChooseCategoryActivity : AppCompatActivity() {
    private val SUB_CATEGORY_REQUEST_CODE = 5002

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choosecategory)

        btn_choosecategory_back.setOnClickListener {
            onBackPressed()
        }

        inflateContent()
    }

    private fun inflateContent() {
        val categoryList = resources.getStringArray(R.array.store_category)
        val extraList = resources.getStringArray(R.array.extra_store_category)
        val layout = layout_choosecategory_scrolllinear
        val inflater = LayoutInflater.from(this)

        val store = TextView(this)
        store.text = "음식점"
        store.textSize = 13f
        layout.addView(store)

        for ((index, name) in categoryList.withIndex()) {
            val view = inflater.inflate(R.layout.item_category, layout, false)
            view.textview_itemcategory_category.text = name
            view.textview_itemcategory_category.setOnClickListener {
                startSubCategory("store", name, index)
            }
            layout.addView(view)
        }

        val extra = TextView(this)
        extra.text = "기타 장소"
        extra.textSize = 13f
        layout.addView(extra)

        for ((index, name) in extraList.withIndex()) {
            val view = inflater.inflate(R.layout.item_category, layout, false)
            view.textview_itemcategory_category.text = name
            view.textview_itemcategory_category.setOnClickListener {
                startSubCategory("extra", name, index)
            }
            layout.addView(view)
        }

    }

    private fun startSubCategory(subTitle: String, name: String?, index: Int) {
        val intent = Intent(this, ChooseSubCategoryActivity::class.java).apply {
            putExtra("subTitle", subTitle)
            putExtra("name", name)
            putExtra("index", index)
        }
        startActivityForResult(intent, SUB_CATEGORY_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == SUB_CATEGORY_REQUEST_CODE) {
            when (resultCode) {
                Activity.RESULT_OK -> {
                    val subCategory = data?.getStringExtra("subCategory")
                    val result = Intent()
                    result.putExtra("category", subCategory)
                    setResult(Activity.RESULT_OK, result)
                    finish()
                }
                else ->
                    Toast.makeText(this, "fail", Toast.LENGTH_LONG).show()
            }
        }
    }
}
