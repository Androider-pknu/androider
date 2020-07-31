package com.professionalandroid.apps.androider.navigation.addpost.addstore.addressing

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.professionalandroid.apps.androider.R
import com.professionalandroid.apps.androider.navigation.addpost.addstore.PickCountryDialog
import kotlinx.android.synthetic.main.activity_searchaddress.*

class SearchAddressActivity : AppCompatActivity(),
    PickCountryDialog.NoticeDialogListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_searchaddress)

        btn_searchaddress_choosecountry.setOnClickListener {
            val countryPicker =
                PickCountryDialog()
            countryPicker.show(supportFragmentManager, "country_picker")
        }

        btn_sample1.setOnClickListener {
            startActivity(Intent(this, ChooseBuildingActivity::class.java))
        }

        btn_searchaddress_cancel.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onDialogCompleteClick(value: String) {
        btn_searchaddress_choosecountry.text = value
    }
}
