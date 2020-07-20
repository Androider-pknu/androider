package com.professionalandroid.apps.androider

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_search_view1.*

class SearchView1 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_view1)
        overridePendingTransition(R.anim.start_search_view,R.anim.stay_search_view)//첫번째 인자는 새로 나타나는 화면이 취하는 애니메이션, 두번째 인자는 지금 화면이 취하는 애니메이션.
        local_search.isIconified=false//SearchView 키보드 띄우기.
        quit.setOnClickListener {
            finish()
            overridePendingTransition(R.anim.stay_search_view,R.anim.end_search_view)
        }
//        local_search.isIconifiedByDefault=false//remove delete button in SearchView
    }
}
