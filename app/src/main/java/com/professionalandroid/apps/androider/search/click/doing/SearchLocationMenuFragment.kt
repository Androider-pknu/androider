package com.professionalandroid.apps.androider.search.click.doing

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.professionalandroid.apps.androider.*
import com.professionalandroid.apps.androider.navigation.SearchFragment.Companion.cfm
import com.professionalandroid.apps.androider.navigation.SearchFragment.Companion.mapFragment
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.fragment_search_location_menu.*

class SearchLocationMenuFragment : Fragment(),
    OnBackPressedListener,
    SearchRelatedCategoryAdapter.OnSRCItemClickListener,
    SearchNearPlaceAdapter.OnSNHItemClickListener {
    private lateinit var searchRelatedAdapter: SearchRelatedCategoryAdapter
    private lateinit var searchNearPlaceAdapter: SearchNearPlaceAdapter
    private lateinit var relatedCategoryList: ArrayList<SearchRelatedCategory>
    private lateinit var nearPlaceList: ArrayList<SearchNearPlace>

    private lateinit var mainAct: MainActivity

    override fun onAttach(context: Context) {
        Log.d("search_current","SearchLocationMenuFragment onAttach")
        super.onAttach(context)
        mainAct = context as MainActivity
        mainAct.setOnBackPressedListener(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search_location_menu,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
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

    private fun initList(){
        relatedCategoryList = ArrayList()
        nearPlaceList = ArrayList()
        for (i in 1..30) {
            nearPlaceList.add(SearchNearPlace("낭만포차", "부산 광역시 남구 대연동", "한식", "120m"))
        }
        for (i in 1..4){
            relatedCategoryList.add(SearchRelatedCategory("낭만포차"))
        }
    }

    override fun onBackPressed() {
        when(cfm.backStackEntryCount){
            0 -> {
                mainAct.setOnBackPressedListener(null)
                mainAct.onBackPressed()
            }
            else -> {
                mainAct.sv_searchview.clearFocus()
                mainAct.sv_searchview.setQuery("",false)
                cfm.popBackStack()
                cfm.popBackStack()
            }
        }
        mainAct.btn_search_cancle.visibility = View.GONE
    }

    // marker 선택 추가예정
    override fun onSRCItemClicked(view: View, position: Int) { // SearchLocationMenuFragment itemClicked
        Log.d("snh_itemClick","SearchLocationMenuFragment(연관 카테고리) itemClicked $position")
        mainAct.sv_searchview.clearFocus()
        cfm.beginTransaction().replace(R.id.fragment_container, mapFragment).addToBackStack(null).commit()

    }

    override fun onSNHItemClicked(view: View, position: Int) {// SearchLocationMenuFragment itemClicked
        val storeName = nearPlaceList[position].nearPlaceName
        Log.d("snh_itemClick","SearchLocationMenuFragment(주변 장소) itemClicked $position")

        mainAct.sv_searchview.clearFocus()
        mainAct.sv_searchview.setQuery(storeName,false)
        cfm.beginTransaction().replace(R.id.fragment_container, mapFragment).addToBackStack(null).commit()
    }
}

