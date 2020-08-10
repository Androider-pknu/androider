package com.professionalandroid.apps.androider.navigation.addpost

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.professionalandroid.apps.androider.R
import com.professionalandroid.apps.androider.navigation.addpost.addressing.ChooseAddressingDialog
import kotlinx.android.synthetic.main.activity_selectstore.*

class SelectStoreActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selectstore)

        // TODO(장소 검색 로직 구현)
        // TODO(현재 장소와 가까운 장소 리스트를 리사이클러 뷰로 표현)

        btn_selectstore_addstore.setOnClickListener {
            val dialog = ChooseAddressingDialog()
            dialog.show(supportFragmentManager, "missiles")
        }
        btn_selectstore_back.setOnClickListener {
            onBackPressed()
        }
    }
}
