package com.professionalandroid.apps.androider.navigation.addpost.insertdata

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.professionalandroid.apps.androider.R
import com.professionalandroid.apps.androider.util.SUB_CATEGORY_REQUEST
import kotlinx.android.synthetic.main.activity_choosecategory.*
import kotlinx.android.synthetic.main.item_category.view.*
import kotlinx.android.synthetic.main.layout_category.view.*

class
ChooseCategoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choosecategory)

        btn_choosecategory_back.setOnClickListener {
            onBackPressed()
        }

        inflateContent(intent.getStringArrayExtra("type"))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == SUB_CATEGORY_REQUEST) {
            when (resultCode) {
                Activity.RESULT_OK -> {
                    setResult(Activity.RESULT_OK, data)
                    finish()
                }
                else ->
                    Toast.makeText(this, "fail", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun inflateContent(type: Array<String>) {
        val inflater = LayoutInflater.from(this)

        for (description in type) {
            val categoryLayout = inflater.inflate(
                R.layout.layout_category, layout_choosecategory_content, false
            )
            layout_choosecategory_content.addView(categoryLayout)

            val targetName = "${description}_category"
            val targetLayout = categoryLayout.layout_category_content
            val descriptionView = categoryLayout.textview_category_description

            when (description) {
                "store" -> descriptionView.text = "음식점"
                "extra" -> descriptionView.text = "기타장소"
                "item" -> targetLayout.removeView(descriptionView)
            }

            val field = R.array::class.java.getField(targetName)
            val categoryList = resources.getStringArray(field.getInt(null))

            for ((index, category) in categoryList.withIndex()) {
                val view = inflater.inflate(R.layout.item_category, targetLayout, false)
                view.textview_itemcategory_category.text = category
                view.textview_itemcategory_category.setOnClickListener {
                    startSubCategory(description, category, index)
                }
                targetLayout.addView(view)
            }
        }
    }

    private fun startSubCategory(description: String, category: String, index: Int) {
        val intent = Intent(this, ChooseSubCategoryActivity::class.java).apply {
            putExtra("description", description)
            putExtra("category", category)
            putExtra("index", index.toString())
        }
        startActivityForResult(intent, SUB_CATEGORY_REQUEST)
    }

}