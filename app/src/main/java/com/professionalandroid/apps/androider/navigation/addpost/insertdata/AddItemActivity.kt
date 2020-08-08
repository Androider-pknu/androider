package com.professionalandroid.apps.androider.navigation.addpost.insertdata

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.professionalandroid.apps.androider.R
import com.professionalandroid.apps.androider.model.ItemDTO
import com.professionalandroid.apps.androider.model.PostDTO
import com.professionalandroid.apps.androider.navigation.addpost.AddPostActivity
import com.professionalandroid.apps.androider.util.AWSRetrofit
import kotlinx.android.synthetic.main.activity_additem.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
            val retrofitAPI = AWSRetrofit.getAPI()
            val name = textinput_additem_name.text.toString()
            val category = textview_additem_selecteditem.text.toString()
            Log.d(this::class.java.simpleName, "insert name: $name, category: $category")

            val call = retrofitAPI.addItem(name, category)
            call.enqueue(object : Callback<ItemDTO> {
                override fun onFailure(call: Call<ItemDTO>, t: Throwable) {
                    Log.d("AddItem::onFailure", "retrofit false")
                    Log.d("AddItem::onResponse", "${t.message}")
                }

                override fun onResponse(call: Call<ItemDTO>, response: Response<ItemDTO>) {
                    Log.d("AddItem::onResponse", "retrofit response")
                    if (response.isSuccessful) {
                        Log.d("AddItem::onResponse", "retrofit successful")

                        val insertedData = response.body()
                        Log.d("AddItem inserted data", insertedData.toString())

                        val intent = Intent(this@AddItemActivity, AddPostActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                        intent.putExtra("resultDTO", insertedData)
                        intent.putExtra("type", PostDTO.ITEM)
                        startActivity(intent)
                    } else {
                        Log.d("AddStore::onResponse", "${response.errorBody()?.string()}")
                    }
                }
            })
        }

        initCompleteCheck()
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

    private fun initCompleteCheck() {
        val essentialList = listOf(
            textinput_additem_name,
            textview_additem_selecteditem
        )
        textinput_additem_name.addTextChangedListener(object :
            CompleteBtnChecker(essentialList) {})
        textview_additem_selecteditem.addTextChangedListener(object :
            CompleteBtnChecker(essentialList) {})
    }

    open inner class CompleteBtnChecker(val essentialList: List<TextView>) : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            for (charSequence in essentialList) {
                if (charSequence.text.isEmpty()) {
                    val color = ContextCompat.getColor(applicationContext, R.color.gray)
                    btn_additem_complete.setTextColor(color)
                    btn_additem_complete.isEnabled = false
                    return
                }
            }
            val color = ContextCompat.getColor(applicationContext, R.color.blue_default)
            btn_additem_complete.setTextColor(color)
            btn_additem_complete.isEnabled = true
        }
    }
}