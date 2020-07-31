package com.professionalandroid.apps.androider.navigation.addpost

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.professionalandroid.apps.androider.R
import kotlinx.android.synthetic.main.activity_addpost.*

class AddPostActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addpost)

        btn_addpost_addstore.setOnClickListener {
            startActivity(Intent(this, SelectStoreActivity::class.java))
        }

        btn_addpost_additem.setOnClickListener {
            startActivity(Intent(this, SelectItemActivity::class.java))
        }

        btn_addpost_cancle.setOnClickListener {
            onBackPressed()
        }

        btn_addpost_complete.setOnClickListener {
            // Upload post, Update Database
        }
    }
}
