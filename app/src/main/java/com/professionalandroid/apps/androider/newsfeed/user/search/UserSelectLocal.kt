package com.professionalandroid.apps.androider.newsfeed.user.search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.professionalandroid.apps.androider.R
import com.professionalandroid.apps.androider.newsfeed.user.UserFrag
import com.professionalandroid.apps.androider.newsfeed.searchlist.EachLocal
import com.professionalandroid.apps.androider.newsfeed.searchlist.EachLocalAdapter
import kotlinx.android.synthetic.main.activity_search_view1.quit
import kotlinx.android.synthetic.main.activity_search_view2.*

class UserSelectLocal : AppCompatActivity() {
    val adapter=
        EachLocalAdapter(makeList())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_view2)
        overridePendingTransition(R.anim.start_search_view, R.anim.stay_search_view)
        quit.setOnClickListener {
            finish()
            overridePendingTransition(R.anim.stay_search_view, R.anim.end_search_view)
        }
        setRecyclerView()
    }
    private fun setRecyclerView(){
        local_list.layoutManager=LinearLayoutManager(this)
        local_list.setHasFixedSize(true)
        adapter.setEachLocalClickListener(object:
            EachLocalAdapter.OnEachLocalClickListener{
            override fun onClick(view: View, position:Int,localName:TextView){
                UserFrag.local_search.text=localName.text as String
                quit.performClick()
            }
        })
        local_list.adapter=adapter
    }
    private fun makeList() : ArrayList<EachLocal>{
        val list= arrayListOf<EachLocal>()
        list.add(
            EachLocal(
                R.drawable.local_location,
                "전체 지역",
                ""
            )
        )
        list.add(
            EachLocal(
                R.drawable.local_location,
                "부산광역시",
                "111Km"
            )
        )
        list.add(
            EachLocal(
                R.drawable.local_location,
                "울산광역시",
                "111Km"
            )
        )
        list.add(
            EachLocal(
                R.drawable.local_location,
                "경상남도",
                "111Km"
            )
        )
        list.add(
            EachLocal(
                R.drawable.local_location,
                "대구광역시",
                "111Km"
            )
        )
        list.add(
            EachLocal(
                R.drawable.local_location,
                "경상북도",
                "111Km"
            )
        )
        list.add(
            EachLocal(
                R.drawable.local_location,
                "전라북도",
                "111Km"
            )
        )
        list.add(
            EachLocal(
                R.drawable.local_location,
                "대전광역시",
                "111Km"
            )
        )
        list.add(
            EachLocal(
                R.drawable.local_location,
                "충청북도",
                "111Km"
            )
        )
        list.add(
            EachLocal(
                R.drawable.local_location,
                "세종특별자치시",
                "111Km"
            )
        )
        list.add(
            EachLocal(
                R.drawable.local_location,
                "전라남도",
                "111Km"
            )
        )
        list.add(
            EachLocal(
                R.drawable.local_location,
                "충청남도",
                "111Km"
            )
        )
        list.add(
            EachLocal(
                R.drawable.local_location,
                "경기도 남부",
                "111Km"
            )
        )
        list.add(
            EachLocal(
                R.drawable.local_location,
                "강원도",
                "111Km"
            )
        )
        list.add(
            EachLocal(
                R.drawable.local_location,
                "제주도",
                "111Km"
            )
        )
        list.add(
            EachLocal(
                R.drawable.local_location,
                "서울특별시",
                "111Km"
            )
        )
        list.add(
            EachLocal(
                R.drawable.local_location,
                "경기도 북부",
                "111Km"
            )
        )
        list.add(
            EachLocal(
                R.drawable.local_location,
                "인천광역시",
                "111Km"
            )
        )
        return list
    }
}
