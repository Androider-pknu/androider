package com.professionalandroid.apps.androider

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_search_main.*
import kotlinx.android.synthetic.main.fragment_search_location_menu.view.*


/* 검색을 하는도중 연관 카테고리와, 근처 장소를 나타나는 프래그먼트*/
class SearchLocationMenuFragment : Fragment() {

    private lateinit var relatedCategoryList: ArrayList<SearchRelatedCategory>
    private lateinit var nearPlaceList: ArrayList<NearPlace>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view : View = inflater.inflate(R.layout.fragment_search_location_menu,container,false)

        initList() /*연관 category 와 nearPlace 리스트를 초기화*/

        /*related category 의 apply 메소드호출
        * adapter 에 relatedCategoryList 를 전달*/
        view?.rv_search_related_category?.apply{
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = SearchRelatedCategoryAdapter(relatedCategoryList)
        }
        /*검색을 하는도중 밑에 뜨는 근처장소
        * searchNearPlaceAdapter 에 nearPlaceList 전달*/
        view?.rv_search_nearPlace_list?.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = SearchNearPlaceAdapter(nearPlaceList)
        }
        // Inflate the layout for this fragment

        return view
    }
    /*연관 category 와 nearPlace 리스트를 초기화*/
    private fun initList(){
        relatedCategoryList = ArrayList()
        nearPlaceList = ArrayList()
        for (i in 1..15) {
            relatedCategoryList.add(SearchRelatedCategory("낭만포차"))
            nearPlaceList.add(NearPlace("낭만포차","부산 광역시 남구 대연동",
                "한식","120m"))
        }
    }
}

