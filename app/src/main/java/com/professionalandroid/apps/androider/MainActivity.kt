package com.professionalandroid.apps.androider

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.core.app.ActivityCompat
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.professionalandroid.apps.androider.navigation.*
import com.professionalandroid.apps.androider.navigation.addpost.AddPostActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {


    private val REQUEST_CODE_PERMISSIONS = 1000
     var mListener: OnBackPressedListener?=null

    override fun onNavigationItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.menu_main -> {
            supportFragmentManager.beginTransaction().replace(R.id.layout_main_content, MainFragment()).commit()
            true
        }
        R.id.menu_news_feed -> {
//            val intent = Intent(this, SampleActivity::class.java)
//            startActivity(intent)
//            true
            supportFragmentManager.beginTransaction().replace(R.id.layout_main_content,NewsFeedFragment(this)).addToBackStack(null).commit()
            true
        }
        R.id.menu_search -> {
            supportFragmentManager.beginTransaction().replace(R.id.layout_main_content, SearchFragment()).addToBackStack(null).commit()
            true
        }
        R.id.menu_alarm -> {
            supportFragmentManager.beginTransaction().replace(R.id.layout_main_content, AlarmFragment()).commit()
            true
        }
        else -> false

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onRequestPermission() // 내위치 요청
        setContentView(R.layout.activity_main)
        navigation_main_bottom.setOnNavigationItemSelectedListener(this)
        navigation_main_bottom.selectedItemId = R.id.menu_main

        btn_main_edit.setOnClickListener {
            val intent = Intent(this, AddPostActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onBackPressed() {
        mListener?.let {
            it.onBackPressed()
            return
        }
        super.onBackPressed()
    }
    /**/
    fun setOnBackPressedListener(listener: OnBackPressedListener?){
        mListener = listener
    }

    /*내 위치 권환을 요청하는 메서드*/
    private fun onRequestPermission() {
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                REQUEST_CODE_PERMISSIONS
            )
            return
        }
    }
}
