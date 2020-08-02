package com.professionalandroid.apps.androider.navigation.addpost.insertdata

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.professionalandroid.apps.androider.R
import kotlinx.android.synthetic.main.activity_inputcategory.*

class InputCategoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inputcategory)

        btn_inputcategory_back.setOnClickListener {
            onBackPressed()
        }

        btn_inputcategory_complete.setOnClickListener {
            val result = Intent()
            result.putExtra("category", intent.getStringExtra("category"))
            result.putExtra("subCategory", textinput_inputcategory_detail.text.toString())
            setResult(Activity.RESULT_OK, result)
            finish()
        }

        textinput_inputcategory_detail.addTextChangedListener(object : TextInputWatcher(){})

        textview_inputcategory_title.text = intent.getStringExtra("category")
    }

    open inner class TextInputWatcher : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            if (s.isEmpty()) {
                val color = ContextCompat.getColor(applicationContext, R.color.gray)
                btn_inputcategory_complete.setTextColor(color)
                btn_inputcategory_complete.isEnabled = false
            } else {
                val color = ContextCompat.getColor(applicationContext, R.color.blue_default)
                btn_inputcategory_complete.setTextColor(color)
                btn_inputcategory_complete.isEnabled = true
            }
        }
    }
}
