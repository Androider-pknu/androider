package com.professionalandroid.apps.androider

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.PermissionChecker.PERMISSION_GRANTED
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.professionalandroid.apps.androider.navigation.AlarmFragment
import com.professionalandroid.apps.androider.navigation.MainFragment
import com.professionalandroid.apps.androider.navigation.NewsFeedFragment
import com.professionalandroid.apps.androider.navigation.SearchFragment
import com.professionalandroid.apps.androider.navigation.addpost.AddPostActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    val LOCATION_PERMISSION_REQUEST = 1001

    override fun onNavigationItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.menu_main -> {
            supportFragmentManager.beginTransaction().replace(R.id.layout_main_content, MainFragment()).commit()
            true
        }
        R.id.menu_news_feed -> {
            supportFragmentManager.beginTransaction().replace(R.id.layout_main_content, NewsFeedFragment()).commit()
            true
        }
        R.id.menu_search -> {
            supportFragmentManager.beginTransaction().replace(R.id.layout_main_content, SearchFragment()).commit()
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
        setContentView(R.layout.activity_main)
        navigation_main_bottom.setOnNavigationItemSelectedListener(this)
        navigation_main_bottom.selectedItemId = R.id.menu_main

        btn_main_edit.setOnClickListener {
            val intent = Intent(this, AddPostActivity::class.java)
            startActivity(intent)
        }

        val permission = ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        if (permission == PERMISSION_GRANTED) {
            Toast.makeText(this, "permission granted", Toast.LENGTH_SHORT).show()
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == LOCATION_PERMISSION_REQUEST) {
            if (grantResults[0] == PERMISSION_GRANTED) {
                Toast.makeText(this, "permission granted", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
