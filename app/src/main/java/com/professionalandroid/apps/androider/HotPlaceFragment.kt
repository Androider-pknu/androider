package com.professionalandroid.apps.androider

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_search_main.*

import kotlinx.android.synthetic.main.fragment_hot_place.view.*

class HotPlaceFragment : Fragment() {
    private lateinit var categoryList: ArrayList<Category>
    //val localList: ArrayList<ItemLocalMaster> = ArrayList()

    /* onCreate는 onCreateView이전에 호출된다.*/
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view : View = inflater.inflate(R.layout.fragment_hot_place,container,false)

        initCategoryList()
        // Inflate the layout for this fragment

        view?.rv_category?.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            setHasFixedSize(true)
            adapter = CategoryAdapter(categoryList)
        }
        return view
    }

    private fun initCategoryList() {
        categoryList = ArrayList()
        for (i in 1..10) {
            categoryList.add(Category("음식점"))
        }
    }
}








