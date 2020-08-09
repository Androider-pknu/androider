package com.professionalandroid.apps.test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.professionalandroid.apps.androider.R
import com.professionalandroid.apps.androider.common.StoreInfoFragment
import com.professionalandroid.apps.androider.model.StoreDTO

class StoreInfoTestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stroe_info_test)

        val storeDTO = StoreDTO(1, "test", "test", "test", "test")

        supportFragmentManager.beginTransaction()
            .add(R.id.test_store_info_container, StoreInfoFragment(storeDTO)).commit()
    }
}