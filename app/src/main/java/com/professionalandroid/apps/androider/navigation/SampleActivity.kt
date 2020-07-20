package com.professionalandroid.apps.androider.navigation

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.professionalandroid.apps.androider.*
import kotlinx.android.synthetic.main.fragment_newsfeed.*

class SampleActivity : AppCompatActivity() {
    ///private lateinit var mContext: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_newsfeed)
        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#FF4500")) //탭바 밑줄 색상 변경.
        val page1= PlaceFrag()
        val page2= ItemFrag()
        val page3= UserFrag()
        makeViewPage(page1,page2,page3)//View Pager 만들기.
        makeTabName()//Tab 마다 이름 설정. 여기서 설정을 안해주면 실행 시 Tab 에 이름이 뜨지 않음.
        //mContext=applicationContext
    }
    private fun makeViewPage (page1:PlaceFrag, page2:ItemFrag, page3:UserFrag){
        val adapter= PageAdapter(supportFragmentManager)
        adapter.addItems(page1)//어뎁터에 원하는 fragment 삽입.
        adapter.addItems(page2)
        adapter.addItems(page3)
        viewPager.adapter=adapter//view pager 에 adapter 장착.
        tabLayout.setupWithViewPager(viewPager)//tabLayout 과 view pager 연동.
    }
    private fun makeTabName(){
        tabLayout.getTabAt(0)?.text = "장소"
        tabLayout.getTabAt(1)?.text = "아이템"
        tabLayout.getTabAt(2)?.text = "사용자"
    }
//    fun nextFragment(fragment: Fragment){
//        supportFragmentManager.beginTransaction()
//            .replace(R.id.main_frame,fragment).addToBackStack(null).commit()
//    }
//    fun nextFragment(fragmentNotUsed:Fragment,fragmentUsed:Fragment){
//        supportFragmentManager.beginTransaction().apply {
//            add(R.id.place,fragmentNotUsed)
//            add(R.id.place,fragmentUsed)
//            show(fragmentUsed)
//            hide(fragmentNotUsed)
//            commit()
//        }
//    }
}