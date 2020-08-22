package com.professionalandroid.apps.androider

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.professionalandroid.apps.androider.util.AWSRetrofit
import com.professionalandroid.apps.androider.util.RetrofitAPI
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_sing_up.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    lateinit var thisActivity: LoginActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        thisActivity=this
        setLogInBtn()
        btn_login_signup.setOnClickListener {
            moveSingUp()
        }
    }

    private fun moveMainPage() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun moveSingUp(){
       startActivity(Intent(this, SingUp::class.java))
    }

    private fun setLogInBtn(){
        btn_login_signin.setOnClickListener {
            val retrofitAPI = AWSRetrofit.getAPI()
            val call = retrofitAPI.signIn(edittext_login_email.text.toString(),edittext_login_password.text.toString())

            call.enqueue(object: Callback<String> {
                override fun onFailure(call: Call<String>, t: Throwable) {
                    Log.d("Test","실패!")
                }
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    if(response.isSuccessful && response.body().toString()=="true") {
                        Toast.makeText(thisActivity, "로그인 완료!", Toast.LENGTH_SHORT).show()
                        moveMainPage()
                    }
                    else if(response.isSuccessful && response.body().toString() == "false"){
                        Toast.makeText(thisActivity, "로그인 실패!", Toast.LENGTH_SHORT).show()
                    }
                }
            })
        }
    }
}
