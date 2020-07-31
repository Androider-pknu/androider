package com.professionalandroid.apps.androider

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.professionalandroid.apps.androider.navigation.SearchFragment.Companion.mapFragment
import kotlinx.android.synthetic.main.fragment_search_location_menu.*

/* 검색을 하는도중 연관 카테고리와, 근처 장소를 나타나는 프래그먼트*/
class SearchLocationMenuFragment : Fragment(), OnBackPressedListener, SearchRelatedCategoryAdapter.OnSRCItemClickListener, SearchNearPlaceAdapter.OnSNHItemClickListener {
    private lateinit var searchRelatedAdapter: SearchRelatedCategoryAdapter
    private lateinit var searchNearPlaceAdapter: SearchNearPlaceAdapter
    private lateinit var relatedCategoryList: ArrayList<SearchRelatedCategory>
    private lateinit var nearPlaceList: ArrayList<NearPlace>

    private lateinit var mainAct: MainActivity
    private lateinit var cfm: FragmentManager

    override fun onAttach(context: Context) {
        Log.d("hakjin","SearchLocationMenuFragment onAttach")
        super.onAttach(context)
        cfm = childFragmentManager
        mainAct = context as MainActivity
        mainAct.setOnBackPressedListener(this)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d("hakjin","SearchLocationMenuFragment onCreateView")
        return inflater.inflate(R.layout.fragment_search_location_menu,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d("hakjin","SearchLocationMenuFragment onViewCreated")
        super.onViewCreated(view, savedInstanceState)
        initList()
        createAdapter()
        manageRecyclerView()
    }

    private fun createAdapter(){ // 어댑터 설정
        searchRelatedAdapter = SearchRelatedCategoryAdapter(relatedCategoryList)
        searchNearPlaceAdapter = SearchNearPlaceAdapter(nearPlaceList)

        searchRelatedAdapter.setOnSRClickListener(this)
        searchNearPlaceAdapter.setOnSNHClickListener(this)
    }

    private fun manageRecyclerView(){
        rv_search_related_category?.apply{
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = searchRelatedAdapter
        }
        rv_search_nearPlace_list?.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter =searchNearPlaceAdapter
        }
    }

    override fun onResume() {
        Log.d("hakjin","SearchLocationMenuFragment onResume()")
        super.onResume()
    }

    override fun onPause() {
        Log.d("hakjin","SearchLocationMenuFragment onPause()")
        super.onPause()
    }

    override fun onDestroy() {
        Log.d("hakjin","SearchLocationMenuFragment onDestroy()")
        super.onDestroy()
    }

    private fun initList(){
        relatedCategoryList = ArrayList()
        nearPlaceList = ArrayList()
        for (i in 1..30) {
            nearPlaceList.add(NearPlace("낭만포차","부산 광역시 남구 대연동",
                "한식","120m"))
        }
        for (i in 1..4){
            relatedCategoryList.add(SearchRelatedCategory("낭만포차"))
        }
    }
    override fun onBackPressed() {
        cfm.popBackStack()
    }

    // marker 선택 추가예정
    override fun onSRCItemClicked(view: View, position: Int) { // SearchLocationMenuFragment itemClicked
        Log.d("hakjin","SearchLocationMenuFragment(연관 카테고리) itemClicked $position")
        mainAct.supportFragmentManager.beginTransaction().replace(R.id.fragment_container,mapFragment).commit()
    }

    override fun onSNHItemClicked(view: View, position: Int) {// SearchLocationMenuFragment itemClicked
        Log.d("hakjin","SearchLocationMenuFragment(주변 장소) itemClicked $position")
        mainAct.supportFragmentManager.beginTransaction().replace(R.id.fragment_container,mapFragment).commit()
    }
}

