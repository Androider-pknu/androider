package com.professionalandroid.apps.androider.navigation.addpost.insertdata

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.professionalandroid.apps.androider.R
import kotlinx.android.synthetic.main.activity_choosesubcategory.*
import kotlinx.android.synthetic.main.item_category.view.*
import kotlinx.android.synthetic.main.item_self_insert.*
import kotlinx.android.synthetic.main.item_self_insert.view.*

class ChooseSubCategoryActivity : AppCompatActivity() {
    val SELF_INSERT_REQUEST_CODE = 5005

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choosesubcategory)

        inflateContent()

        btn_choosesubcategory_back.setOnClickListener {
            onBackPressed()
        }

        btn_choosesubcategory_selfinput.setOnClickListener {
            val intent = Intent(this, ChooseSubCategoryActivity::class.java)
            intent.putExtra("name", textview_choosesubcategory_supercategory.text)
            startActivityForResult(intent, SELF_INSERT_REQUEST_CODE)
        }

        btn_choosesubcategory_complete.setOnClickListener {
            val result = Intent()
            result.putExtra("subCategory", textinput_selfinsert_detail.text.toString())
            setResult(Activity.RESULT_OK, result)
            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == SELF_INSERT_REQUEST_CODE) {
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

    private fun inflateContent() {
        val inflater = LayoutInflater.from(this)
        val intent = intent
        val prefix = when (intent.getStringExtra("subTitle")) {
            "store" -> "sub_store_category"
            "extra" -> "sub_extra_category"
            "item" -> "item_sub_category"
            else -> "self"
        }

        textview_choosesubcategory_supercategory.text = intent.getStringExtra("name")

        if (prefix != "self") {
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
        } else {
            btn_choosesubcategory_complete.visibility = Button.VISIBLE
            btn_choosesubcategory_selfinput.visibility = Button.INVISIBLE
            val view = inflater.inflate(
                R.layout.item_self_insert,
                layout_choosesubcategory_subcategory,
                false
            )
            view.textinput_selfinsert_detail.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    if (s.isEmpty()) {
                        val color = ContextCompat.getColor(applicationContext, R.color.gray)
                        btn_choosesubcategory_complete.setTextColor(color)
                        btn_choosesubcategory_complete.isEnabled = false
                    } else {
                        val color = ContextCompat.getColor(applicationContext, R.color.blue_default)
                        btn_choosesubcategory_complete.setTextColor(color)
                        btn_choosesubcategory_complete.isEnabled = true
                    }
                }
            })
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
