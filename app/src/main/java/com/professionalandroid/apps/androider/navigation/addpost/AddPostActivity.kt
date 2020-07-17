package com.professionalandroid.apps.androider.navigation.addpost

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.professionalandroid.apps.androider.R
import kotlinx.android.synthetic.main.activity_addpost.*

class AddPostActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addpost)
        btn_addpost_addstore.setOnClickListener {
            startActivity(Intent(this, SelectStoreActivity::class.java))
        }
    }
}
