package com.professionalandroid.apps.androider.navigation.addpost.addstore

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.professionalandroid.apps.androider.R
import com.professionalandroid.apps.androider.navigation.addpost.addstore.input.AddStoreActivity
import kotlinx.android.synthetic.main.activity_choosebuilding.*

class ChooseBuildingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choosebuilding)
        btn_sample.setOnClickListener {
            startActivity(Intent(this, AddStoreActivity::class.java))
        }
        btn_choosebuilding_building.setOnClickListener {
            btn_choosebuilding_building.setTextColor(ContextCompat.getColor(applicationContext, R.color.white))
            btn_choosebuilding_location.setTextColor(ContextCompat.getColor(applicationContext, R.color.blue_default))
        }
        btn_choosebuilding_location.setOnClickListener {
            btn_choosebuilding_building.setTextColor(ContextCompat.getColor(applicationContext, R.color.blue_default))
            btn_choosebuilding_location.setTextColor(ContextCompat.getColor(applicationContext, R.color.white))
        }
        btn_choosebuilding_back.setOnClickListener {
            onBackPressed()
        }
    }
}
