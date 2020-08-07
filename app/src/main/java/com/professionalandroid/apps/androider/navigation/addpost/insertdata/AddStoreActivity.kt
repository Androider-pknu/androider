package com.professionalandroid.apps.androider.navigation.addpost.insertdata

import android.app.Activity
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP
import android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.professionalandroid.apps.androider.R
import com.professionalandroid.apps.androider.model.StoreDTO
import com.professionalandroid.apps.androider.navigation.addpost.AddPostActivity
import com.professionalandroid.apps.androider.navigation.addpost.addressing.ChangeAddressActivity
import com.professionalandroid.apps.androider.util.AWSRetrofit
import kotlinx.android.synthetic.main.activity_addstore.*
import kotlinx.android.synthetic.main.activity_searchaddress.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddStoreActivity : AppCompatActivity() {
    private val STORE_CATEGORY_REQUEST = 5001
    private val CHANGE_ADDRESS_REQUEST = 8001
    var address: String? = ""
    var building: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addstore)

        layout_addstore_category.setOnClickListener {
            val intent = Intent(this, ChooseCategoryActivity::class.java)
            intent.putExtra("type", arrayOf("store", "extra"))
            startActivityForResult(
                intent,
                STORE_CATEGORY_REQUEST
            )
        }

        layout_addstore_address.setOnClickListener {
            val intent = Intent(this, ChangeAddressActivity::class.java)
            intent.putExtra("address", address)
            intent.putExtra("building", building)
            startActivityForResult(intent, CHANGE_ADDRESS_REQUEST)
        }

        btn_addstore_back.setOnClickListener {
            onBackPressed()
        }

        btn_addstore_complete.setOnClickListener {
            // TODO
            // make store data model
            // insert data model to database
            // return data model to add post activity
            val retrofitAPI = AWSRetrofit.getAPI()
            val name = textinput_addstore_name.text.toString()
            val category = textview_addstore_category.text.toString()
            val address = textview_addstore_address.text.toString()
            val number = textinput_addstore_number.text.toString()
            Log.d(
                this::class.java.simpleName,
                "insert name: $name, category: $category, address: $address, number: $number"
            )
            val call = retrofitAPI.insertStore(name, category, address, number)
            call.enqueue(object : Callback<StoreDTO> {
                override fun onFailure(call: Call<StoreDTO>, t: Throwable) {
                    Log.d("AddStore::onFailure", "retrofit false")
                    Log.d("AddStore::onResponse", "${t.message}")
                }

                override fun onResponse(call: Call<StoreDTO>, response: Response<StoreDTO>) {
                    Log.d("AddStore::onResponse", "retrofit response")
                    if (response.isSuccessful) {
                        Log.d("AddStore::onResponse", "retrofit successful")

                        val insertedData = response.body()
                        Log.d("AddStore inserted data", insertedData.toString())

                        val intent = Intent(this@AddStoreActivity, AddPostActivity::class.java)
                        intent.addFlags(FLAG_ACTIVITY_CLEAR_TOP)
                        intent.addFlags(FLAG_ACTIVITY_SINGLE_TOP)
                        intent.putExtra("storeDTO", insertedData)
                        startActivity(intent)
                    } else {
                        Log.d("AddStore::onResponse", "${response.errorBody()?.string()}")
                    }
                }
            })
        }

        initCompleteCheck()

        address = intent.getStringExtra("address")
        textview_addstore_address.text = address
    }

    private fun initCompleteCheck() {
        val essentialList = listOf(
            textview_addstore_address,
            textview_addstore_category,
            textinput_addstore_name
        )
        textinput_addstore_name.addTextChangedListener(object :
            CompleteBtnChecker(essentialList) {})
        textview_addstore_address.addTextChangedListener(object :
            CompleteBtnChecker(essentialList) {})
        textview_addstore_category.addTextChangedListener(object :
            CompleteBtnChecker(essentialList) {})
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            STORE_CATEGORY_REQUEST -> {
                when (resultCode) {
                    Activity.RESULT_OK -> {
                        val category = data?.getStringExtra("subCategory")
                        textview_addstore_category.text = category
                    }
                    else -> Toast.makeText(this, "fail", Toast.LENGTH_LONG).show()
                }
            }
            CHANGE_ADDRESS_REQUEST -> {
                when (resultCode) {
                    Activity.RESULT_OK -> {
                        address = data?.getStringExtra("address")
                        building = data?.getStringExtra("building")
                        val display = "$address $building"
                        textview_addstore_address.text = display
                    }
                    else -> Toast.makeText(this, "fail", Toast.LENGTH_LONG).show()
                }
            }
        }
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
                    btn_addstore_complete.setTextColor(color)
                    btn_addstore_complete.isEnabled = false
                    return
                }
            }
            val color = ContextCompat.getColor(applicationContext, R.color.blue_default)
            btn_addstore_complete.setTextColor(color)
            btn_addstore_complete.isEnabled = true
        }
    }
}
