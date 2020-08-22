package com.professionalandroid.apps.androider

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.professionalandroid.apps.androider.util.AWSRetrofit
import kotlinx.android.synthetic.main.activity_sing_up.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SingUp : AppCompatActivity() {
    lateinit var thisActivity: SingUp
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sing_up)
        thisActivity=this
        setButton()
    }

    private fun setButton(){
        sign_up_complete.setOnClickListener {
            val retrofitAPI = AWSRetrofit.getAPI()
            val call = retrofitAPI.singUp(sign_up_input_id.text.toString(), sign_up_input_pw.text.toString(),
                sign_up_input_email.text.toString(),sign_up_input_nickname.text.toString())

            call.enqueue(object: Callback<Unit> {
                override fun onFailure(call: Call<Unit>, t: Throwable) {
                    Log.d("Test","실패!")
                }
                override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                    if(response.isSuccessful) {
                        Log.d("Test", "성공!")
                        Toast.makeText(thisActivity, "회원 가입 성공!", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }
            })
        }
    }
    private fun singUp(){
        val retrofitAPI = AWSRetrofit.getAPI()
        val call = retrofitAPI.singUp(sign_up_input_id.text.toString(), sign_up_input_pw.text.toString(),
            sign_up_input_email.text.toString(),sign_up_input_nickname.text.toString())

        call.enqueue(object: Callback<Unit> {
            override fun onFailure(call: Call<Unit>, t: Throwable) {
                Log.d("Test","실패!")
            }
            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                if(response.isSuccessful)
                    Toast.makeText(thisActivity,"회원 가입 성공!",Toast.LENGTH_SHORT).show()
            }
        })
    }
}
