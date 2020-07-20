package com.professionalandroid.apps.androider.navigation

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.professionalandroid.apps.androider.*
import kotlinx.android.synthetic.main.fragment_newsfeed.*
import kotlinx.android.synthetic.main.fragment_newsfeed.view.*

class NewsFeedFragment : Fragment() {
    lateinit var rootView: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.fragment_newsfeed, container, false)
        rootView.tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#FF4500")) //탭바 밑줄 색상 변경.
        val page1 = PlaceFrag()
        val page2 = ItemFrag()
        val page3 = UserFrag()
        makeViewPage(page1, page2, page3)//View Pager 만들기.
        makeTabName()//Tab 마다 이름 설정. 여기서 설정을 안해주면 실행 시 Tab 에 이름이 뜨지 않음.

        return rootView
    }

    private fun makeViewPage(page1: PlaceFrag, page2: ItemFrag, page3: UserFrag) {
        val adapter = PageAdapter((activity as AppCompatActivity).supportFragmentManager)
        adapter.addItems(page1)//어뎁터에 원하는 fragment 삽입.
        adapter.addItems(page2)
        adapter.addItems(page3)
        rootView.viewPager.adapter = adapter//view pager 에 adapter 장착.
        rootView.tabLayout.setupWithViewPager(viewPager)//tabLayout 과 view pager 연동.
    }

    private fun makeTabName() {
        rootView.tabLayout.getTabAt(0)?.text = "장소"
        rootView.tabLayout.getTabAt(1)?.text = "아이템"
        rootView.tabLayout.getTabAt(2)?.text = "사용자"
    }
}