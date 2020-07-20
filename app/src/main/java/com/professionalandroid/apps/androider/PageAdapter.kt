package com.professionalandroid.apps.androider

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class PageAdapter (fm:FragmentManager): FragmentStatePagerAdapter(fm){
    private var fragments : ArrayList<Fragment> = ArrayList()// 뷰 페이저와 연동시킬 fragment 들을 모아둔 곳.
    override fun getItem(position:Int): Fragment=fragments[position]//position 에 위치한 fragment 를 반환함.
    override fun getCount(): Int =fragments.size
    fun addItems(fragment: Fragment) {
        fragments.add(fragment)//내가 원하는 fragment 삽입.
    }
}