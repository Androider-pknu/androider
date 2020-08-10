package com.professionalandroid.apps.androider.navigation.addpost

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.professionalandroid.apps.androider.R
import com.professionalandroid.apps.androider.navigation.addpost.insertdata.AddItemActivity
import kotlinx.android.synthetic.main.activity_selectitem.*

class SelectItemActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selectitem)

        btn_selectitem_back.setOnClickListener {
            onBackPressed()
        }

        // TODO(아이템 검색 로직 구현)
        // TODO(최근 등록된 아이템 리스트를 리사이클러 뷰로 표현)

        btn_selectitem_additem.setOnClickListener {
            startActivity(Intent(this, AddItemActivity::class.java))
        }
    }
}