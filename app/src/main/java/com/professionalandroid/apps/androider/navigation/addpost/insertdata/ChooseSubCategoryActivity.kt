package com.professionalandroid.apps.androider.navigation.addpost.insertdata

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.professionalandroid.apps.androider.R
import kotlinx.android.synthetic.main.activity_choosesubcategory.*
import kotlinx.android.synthetic.main.item_category.view.*

class ChooseSubCategoryActivity : AppCompatActivity() {
    val INPUT_CATEGORY_REQUEST = 7001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choosesubcategory)

        inflateContent(intent.getStringExtra("description"))

        btn_choosesubcategory_back.setOnClickListener {
            onBackPressed()
        }

        btn_choosesubcategory_selfinput.setOnClickListener {
            val intent = Intent(this, InputCategoryActivity::class.java)
            intent.putExtra("category", textview_choosesubcategory_supercategory.text)
            startActivityForResult(intent, INPUT_CATEGORY_REQUEST)
        }
    }

    private fun inflateContent(description: String) {
        val inflater = LayoutInflater.from(this)
        val targetLayout = layout_choosesubcategory_content

        val category = intent.getStringExtra("category")
        val index = intent.getStringExtra("index")
        val targetName = "${description}_sub_category_${index}"

        textview_choosesubcategory_supercategory.text = category

        val field = R.array::class.java.getField(targetName)
        val subCategoryList = resources.getStringArray(field.getInt(null))

        for (subCategory in subCategoryList) {
            val view = inflater.inflate(R.layout.item_category, targetLayout, false)
            view.textview_itemcategory_category.text = subCategory
            view.textview_itemcategory_category.setOnClickListener {
                returnResult(category, subCategory)
            }
            layout_choosesubcategory_content.addView(view)
        }

    }

    private fun returnResult(category: String, subCategory: String) {
        val result = Intent()
        result.putExtra("category", category)
        result.putExtra("subCategory", subCategory)
        setResult(Activity.RESULT_OK, result)
        finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == INPUT_CATEGORY_REQUEST) {
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
}
