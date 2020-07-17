package com.professionalandroid.apps.androider

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btn_login_signin.setOnClickListener {
            moveMainPage()
        }

        btn_login_signup.setOnClickListener {
            moveMainPage()
        }
    }

    private fun moveMainPage() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
