package com.professionalandroid.apps.androider.navigation.addpost.insertdata

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.professionalandroid.apps.androider.R
import com.professionalandroid.apps.androider.navigation.addpost.addressing.ChangeAddressActivity
import kotlinx.android.synthetic.main.activity_addstore.*

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
        }

        address = intent.getStringExtra("address")
        textview_addstore_address.text = address
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
}
