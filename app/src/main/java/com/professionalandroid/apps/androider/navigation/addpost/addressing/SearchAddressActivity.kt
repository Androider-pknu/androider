package com.professionalandroid.apps.androider.navigation.addpost.addressing

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.GsonBuilder
import com.professionalandroid.apps.androider.R
import com.professionalandroid.apps.androider.model.AddressModel
import com.professionalandroid.apps.androider.util.AWSRetrofit
import com.professionalandroid.apps.androider.util.RetrofitAPI
import com.professionalandroid.apps.androider.util.SEARCH_ADDRESS_REQUEST
import kotlinx.android.synthetic.main.activity_searchaddress.*
import kotlinx.android.synthetic.main.item_category.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SearchAddressActivity : AppCompatActivity(), PickCountryDialog.NoticeDialogListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_searchaddress)

        btn_searchaddress_choosecountry.setOnClickListener {
            val countryPicker =
                PickCountryDialog()
            countryPicker.show(supportFragmentManager, "country_picker")
        }

        btn_searchaddress_cancel.setOnClickListener {
            onBackPressed()
        }

        searchview_searchaddress.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                searchAddress(searchview_searchaddress.text.toString())
            }
            false
        }
    }

    private fun searchAddress(address: String) {
        val retrofitAPI = AWSRetrofit.getAPI()
        val call = retrofitAPI.searchAddress(address)

        call.enqueue(object : Callback<AddressModel> {
            override fun onFailure(call: Call<AddressModel>, t: Throwable) {
                Toast.makeText(this@SearchAddressActivity, "search fail", Toast.LENGTH_SHORT).show()
                Log.d(SearchAddressActivity::class.java.simpleName, t.message)
            }

            override fun onResponse(call: Call<AddressModel>, response: Response<AddressModel>) {
                if (response.isSuccessful) {
                    layout_searchaddress_addresslist.removeAllViews()
                    addAddressItem(response.body())
                }
            }
        })
    }

    private fun addAddressItem(result: AddressModel?) {
        val addressList = result?.results?.juso ?: emptyList()
        val inflater = LayoutInflater.from(this)

        if (addressList.isNotEmpty()) {
            for (juso in addressList) {
                val view = inflater.inflate(
                    R.layout.item_category, layout_searchaddress_addresslist, false
                )
                view.textview_itemcategory_category.text = juso.roadAddrPart1

                if (intent.getBooleanExtra("changeAddress", false)) {
                    view.textview_itemcategory_category.setOnClickListener {
                        val intent = Intent(this, ChooseBuildingActivity::class.java)
                        intent.putExtra(
                            "address", view.textview_itemcategory_category.text.toString()
                        )
                        intent.putExtra("changeAddress", true)
                        startActivityForResult(intent, SEARCH_ADDRESS_REQUEST)
                    }
                } else {
                    view.textview_itemcategory_category.setOnClickListener {
                        val intent = Intent(this, ChooseBuildingActivity::class.java)
                        intent.putExtra(
                            "address", view.textview_itemcategory_category.text.toString()
                        )
                        startActivity(intent)
                    }
                }
                layout_searchaddress_addresslist.addView(view)
            }
        }
    }

    override fun onDialogCompleteClick(value: String) {
        btn_searchaddress_choosecountry.text = value
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == SEARCH_ADDRESS_REQUEST) {
            when (resultCode) {
                Activity.RESULT_OK -> {
                    setResult(Activity.RESULT_OK, data)
                    finish()
                }
            }
        }
    }

}
