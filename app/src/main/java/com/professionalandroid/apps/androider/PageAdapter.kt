package com.professionalandroid.apps.androider

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter

//class PageAdapter (fm:FragmentManager): FragmentStatePagerAdapter(fm){ ViewPager 1
//    private var fragments : ArrayList<Fragment> = ArrayList()// 뷰 페이저와 연동시킬 fragment 들을 모아둔 곳.
//    override fun getItem(position:Int): Fragment=fragments[position]//position 에 위치한 fragment 를 반환함.
//    override fun getCount(): Int =fragments.size
//    override fun getItemPosition(`object`: Any): Int {
//        //return super.getItemPosition(`object`)
//        return PagerAdapter.POSITION_NONE
//    }
//    fun addItems(fragment: Fragment) {
//        fragments.add(fragment)//내가 원하는 fragment 삽입.
//    }
//}

class PageAdapter(fa:FragmentActivity): FragmentStateAdapter(fa){ //View Pager 2
    lateinit var fragmentItems:List<Fragment>
    fun getItem(position: Int): Fragment {
        return when(position){
            0 -> fragmentItems[0]
            1-> fragmentItems[1]
            else -> fragmentItems[2]
        }
    }

    override fun getItemCount(): Int {
        return 3
    }
    override fun createFragment(position: Int):Fragment{
        return when(position){
            0 -> fragmentItems[0]
            1-> fragmentItems[1]
            else-> fragmentItems[2]
        }
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }
    fun setFragment(items:List<Fragment>){
        fragmentItems=items
    }
}