package com.professionalandroid.apps.androider.navigation

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.gms.maps.model.LatLng
import com.professionalandroid.apps.androider.*
import com.professionalandroid.apps.androider.search.click.HotPlaceFragment
import com.professionalandroid.apps.androider.search.map.MainMapFragment
import com.professionalandroid.apps.androider.search.click.doing.SearchLocationMenuFragment
import com.professionalandroid.apps.androider.search.click.result.SearchResultFragment
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment: Fragment(), OnBackPressedListener{

    private lateinit var mainAct: MainActivity
    private lateinit var cfm: FragmentManager

    private lateinit var hotPlaceFragment: HotPlaceFragment
    lateinit var searchLocationMenuFragment: SearchLocationMenuFragment
    lateinit var searchResultFragment: SearchResultFragment

    private var flag = false // 검색결과 맵 마커표시 false -> 맵 X, true -> 맵 O

    companion object{
        lateinit var mCurrentCameraLocation: LatLng
        lateinit var mapFragment: MainMapFragment
        var searchOnQueryFlag = false
    }

    override fun onAttach(context: Context) {
        Log.d("hakjin","SearchFragment onAttach")
        super.onAttach(context)
        cfm = childFragmentManager
        mainAct = context as MainActivity
        mainAct.setOnBackPressedListener(this)
        locationPermissionCheck()
        createFragment()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("hakjin","SearchFragment onCreate")
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d("hakjin","SearchFragment  onCreateView")

        val view = inflater.inflate(R.layout.fragment_search,container,false)
        cfm.beginTransaction().add(R.id.fragment_container,mapFragment).commit()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d("hakjin","SearchFragment onViewCreated")
        super.onViewCreated(view, savedInstanceState)

        searchViewManage()
        buttonClickManage()
    }

    override fun onResume() {
        Log.d("hakjin","SearchFragment onResume()")
        super.onResume()
    }

    override fun onPause() {
        Log.d("hakjin","SearchFragment onPause()")
        super.onPause()
    }

    override fun onDestroy() {
        Log.d("hakjin","SearchFragment onDestroy()")
        super.onDestroy()
    }

    private fun createFragment(){
        mapFragment = MainMapFragment()
        hotPlaceFragment = HotPlaceFragment()
        searchLocationMenuFragment = SearchLocationMenuFragment()
        searchResultFragment = SearchResultFragment()
    }

    private fun searchViewManage(){
        searchViewFocusChange()
        setOnQueryTextChange()
    }

    private fun buttonClickManage(){
        btn_search_cancle.setOnClickListener {
            sv_searchview.clearFocus()
            btn_search_result_map.visibility = View.GONE
            mapFragment.markerUpdate(false)
            onBackPressed()
            cfm.popBackStack()
            sv_searchview.setQuery("",false)
        }

        btn_search_result_map.setOnClickListener {
            changeResultMapState()
        }
    }

    private fun changeResultMapState(){
        mapFragment.markerFlag = false // 현재 "검색결과맵 마커"가 사용중이다
        when(flag){
            false -> { // 마커 O
                Log.d("hakjin"," resultMap click - false")
                btn_search_result_map.text ="목록"
                cfm.beginTransaction().replace(R.id.fragment_container,mapFragment).addToBackStack(null).commit()
                mapFragment.markerUpdate(true)
                flag = true
            }
            true -> { // 마커 X
                Log.d("hakjin"," resultMap click - true")
                btn_search_result_map.text ="지도"
                mapFragment.markerUpdate(false)
                cfm.popBackStack()
                flag = false
            }
        }
    }

    private fun searchViewFocusChange(){
        sv_searchview.setOnQueryTextFocusChangeListener{ view: View, b: Boolean ->
            Log.d("hakjin","serchViewFocusChange 호출")
            if(cfm.findFragmentByTag("HP")==null)
                cfm.beginTransaction().replace(R.id.fragment_container, hotPlaceFragment,"HP").addToBackStack(null).commit()
            searchOnQueryFlag = true
            changeStateCloseButton(true)
        }
    }

    private fun setOnQueryTextChange(){
        sv_searchview.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                Log.d("hakjin", "onQueryTextSubmit 메소드 호출")
                sv_searchview.clearFocus()
                if (cfm.findFragmentByTag("SRF") == null) {
                    cfm.beginTransaction().replace(R.id.fragment_container, searchResultFragment, "SRF").addToBackStack(null).commit()
                    btn_search_result_map.visibility = View.VISIBLE
                }
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                Log.d("hakjin","OnQeryTextChange 메소드 호출")
                btn_search_result_map.visibility = View.GONE
                if(sv_searchview.query.isEmpty()) {
                    Log.d("hakjin","현재문자열 비었다")
                    onBackPressed()
                    cfm.popBackStack()
                    sv_searchview.clearFocus()
                }
                else{
                    Log.d("hakjin","현재문자열 비지 않았다")
                    if(searchOnQueryFlag)
                        if(cfm.findFragmentByTag("SLM")==null)
                            cfm.beginTransaction().replace(R.id.fragment_container,searchLocationMenuFragment,"SLM").addToBackStack(null).commit()
                }
                return false
            }
        })
    }

    override fun onBackPressed() {
        val i = cfm.backStackEntryCount
        Log.d("hakjin",i.toString())
        when(cfm.backStackEntryCount){
            0 -> {
                mainAct.setOnBackPressedListener(null)
                mainAct.onBackPressed()
                Log.d("hakjin","searchfragment 0")
            }
            else -> {
                Log.d("hakjin","searchfragment else")
                cfm.popBackStack()
            }
        }
    }

    private fun changeStateCloseButton(check : Boolean){ // 수정예정
        when(check){
            false -> btn_search_cancle.visibility = View.GONE
            true -> btn_search_cancle.visibility = View.VISIBLE
        }
    }

    private fun locationPermissionCheck() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ), 1000
            )
            return
        }
    }
}


