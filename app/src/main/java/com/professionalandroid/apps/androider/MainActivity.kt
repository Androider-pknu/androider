package com.professionalandroid.apps.androider

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.professionalandroid.apps.androider.navigation.AlarmFragment
import com.professionalandroid.apps.androider.navigation.MainFragment
import com.professionalandroid.apps.androider.navigation.NewsFeedFragment
import com.professionalandroid.apps.androider.navigation.SearchFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    override fun onNavigationItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.menu_main -> {
            supportFragmentManager.beginTransaction().replace(R.id.main_frame, MainFragment()).commit()
            true
        }
        R.id.menu_news_feed -> {
            supportFragmentManager.beginTransaction().replace(R.id.main_frame, NewsFeedFragment()).commit()
            true
        }
        R.id.menu_write -> {
            true
        }
        R.id.menu_search -> {
            supportFragmentManager.beginTransaction().replace(R.id.main_frame, SearchFragment()).commit()
            true
        }
        R.id.menu_alarm -> {
            supportFragmentManager.beginTransaction().replace(R.id.main_frame, AlarmFragment()).commit()
            true
        }
        else -> false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottom_navigation_view.setOnNavigationItemSelectedListener(this)
        bottom_navigation_view.selectedItemId = R.id.menu_main
    }
}
