package com.professionalandroid.apps.androider

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.professionalandroid.apps.androider.navigation.*
import com.professionalandroid.apps.androider.navigation.addpost.AddPostActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

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
    }
}
