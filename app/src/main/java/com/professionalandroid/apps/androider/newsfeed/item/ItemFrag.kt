package com.professionalandroid.apps.androider.newsfeed.item

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.professionalandroid.apps.androider.R
import com.professionalandroid.apps.androider.newsfeed.CategoryAll
import com.professionalandroid.apps.androider.newsfeed.CategoryOther
import com.professionalandroid.apps.androider.newsfeed.KindOfFood
import com.professionalandroid.apps.androider.newsfeed.KindOfFoodAdapter
import kotlinx.android.synthetic.main.fragment2.view.*

class ItemFrag():Fragment(){
    //var fragment = ItemSearchFrag(newsFeedView)
    var subAllFragment= CategoryAll()
    var categoryAdapter= KindOfFoodAdapter(makeList1())
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        val view=inflater.inflate(R.layout.fragment2,container,false)
        defaultScreen()
        //setSearchButton(view)
        setRecyclerView1(view)
        //setRecyclerView2(view)
        return view
    }
    private fun defaultScreen(){
        requireActivity().supportFragmentManager.beginTransaction().replace(R.id.category_frame,subAllFragment).commit()
    }
    private fun setRecyclerView1(view: View){
        view.category_item.setHasFixedSize(true)
        categoryAdapter.setCategoryClickListener(object :
            KindOfFoodAdapter.OnCategoryClickListener {
            override fun onClick(view: View, position: Int,flag:Boolean) {
                if(!flag)requireActivity().supportFragmentManager.beginTransaction().replace(
                    R.id.category_frame,CategoryOther(position+1)).commit()
                //position+1 을 인자로 넘겨주는 이유는 post 테이블에서 type 이 정해져있는데 type  음식/메뉴 ~ 기타 와 2~15가 서로 대응되기에
                //position+1 을 넘겨 주었음.
                else defaultScreen()
            }
        })
        view.category_item.adapter=categoryAdapter
        view.category_item.layoutManager=LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
    }
    private fun makeList1():ArrayList<KindOfFood>{
        val list= arrayListOf<KindOfFood>()
        list.add(KindOfFood("전체"))
        list.add(KindOfFood("음식/메뉴"))
        list.add(KindOfFood("카페"))
        list.add(KindOfFood("편의점"))
        list.add(KindOfFood("냉동/반조리"))
        list.add(KindOfFood("라면"))
        list.add(KindOfFood("간식/간편식"))
        list.add(KindOfFood("과자/빙과류"))
        list.add(KindOfFood("빵/떡"))
        list.add(KindOfFood("음료"))
        list.add(KindOfFood("주류"))
        list.add(KindOfFood("과일/식재료"))
        list.add(KindOfFood("요리"))
        list.add(KindOfFood("문화"))
        list.add(KindOfFood("기타"))
        return list
    }
}