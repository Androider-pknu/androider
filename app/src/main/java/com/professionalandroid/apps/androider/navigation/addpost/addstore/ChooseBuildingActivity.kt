package com.professionalandroid.apps.androider.navigation.addpost.addstore

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
    }
}
