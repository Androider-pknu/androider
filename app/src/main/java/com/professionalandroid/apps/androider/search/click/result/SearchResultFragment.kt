package com.professionalandroid.apps.androider.search.click.result

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.tabs.TabLayout
import com.professionalandroid.apps.androider.MainActivity
import com.professionalandroid.apps.androider.OnBackPressedListener
import com.professionalandroid.apps.androider.OnMapCreatedViewListener
import com.professionalandroid.apps.androider.R
import com.professionalandroid.apps.androider.model.StoreDTO
import com.professionalandroid.apps.androider.navigation.SearchFragment.Companion.cfm
import com.professionalandroid.apps.androider.navigation.SearchFragment.Companion.mapFragment
import com.professionalandroid.apps.androider.search.map.marker.LMMarkerItem
import com.professionalandroid.apps.androider.util.AWSRetrofit
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.fragment_search_result.*
import kotlinx.android.synthetic.main.fragment_search_result.view.rv_search_result
import retrofit2.Callback
import retrofit2.Response

class SearchResultFragment : Fragment(),
    OnBackPressedListener, SearchResultAdapter.OnSRItemClickListener,
    OnMapCreatedViewListener {

    private var tabSelect = 1
    private lateinit var resultAdapter: SearchResultAdapter
    private lateinit var mainAct: MainActivity

    var rootView: View? = null

    private val locationList = ArrayList<LMMarkerItem>() // Marker Location List
    private var recommendList = ArrayList<StoreDTO>()
    private var nearPlaceList = ArrayList<StoreDTO>()

    private var mapRadius: Double = 0.0 // mapRadius
    private lateinit var cameraPosition: LatLng
    var loc: LatLng? = null// clicked marker position

    override fun onAttach(context: Context) {
        Log.d("SearchResult","SearchResultFragment OnAttach")
        super.onAttach(context)
        tabSelect = 1
        resultAdapter = SearchResultAdapter()
        configureRadius()
        getStoreData()
        mainAct = context as MainActivity
        mainAct.setOnBackPressedListener(this)
        mapFragment.setOnMapCreatedViewFinish(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if(rootView==null)
            rootView = inflater.inflate(R.layout.fragment_search_result, container, false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setResultAdapter()
        manageTabLayout()
        clickListenerMange()
        mainAct.btn_search_result_map.setOnClickListener {
            when(mainAct.btn_search_result_map.text){
                "지도" -> changeResultMapState(false)
                "목록"-> changeResultMapState(true)
            }
        }
    }

    fun changeResultMapState(flag: Boolean){
        mapFragment.markerFlag = false // Marker Used From Search Result
        when(flag){
            false -> { // 마커 O
                mainAct.btn_search_result_map.text ="목록"
                cfm.beginTransaction().replace(R.id.fragment_container,mapFragment).addToBackStack(null).commit()
                initMarkerLocation()
                mapFragment.markerAdd(locationList,false,-1)
            }
            true -> { // 마커 X
                mainAct.sv_searchview.clearFocus()
                mainAct.btn_search_result_map.text ="지도"
                mapFragment.markerDelete()
                cfm.popBackStack()
            }
        }
    }

    private fun clickListenerMange(){
        resultAdapter.setOnSRClickListener(this)
    }

    private fun manageTabLayout(){
        search_tab_layout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabReselected(tab: TabLayout.Tab?) { /* adapter 에 넣을 리스트를 초기화 0 -> 주변장소 1 -> 추천장소 */
                when (tab?.position){
                    0 -> {
                        resultAdapter.setList(nearPlaceList)
                        tabSelect = 1
                    }
                    1 -> {
                        resultAdapter.setList(recommendList)
                        tabSelect = 2
                    }
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position){
                    0 -> {
                        resultAdapter.setList(nearPlaceList)
                        tabSelect = 1
                    }
                    1 -> {
                        resultAdapter.setList(recommendList)
                        tabSelect = 2
                    }
                }
            }
        })
    }

    private fun setResultAdapter() { // Set Adapter
        rv_search_result.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            setHasFixedSize(true)
            rv_search_result.adapter = resultAdapter
        }
    }

    private fun getStoreData(){ // Get Store Information
        val string = arguments?.getString("searchresult").toString()
        val retrofitAPI = AWSRetrofit.getAPI()
        val call = retrofitAPI.getStore(string,mapRadius,cameraPosition.latitude,cameraPosition.longitude)
            call.enqueue(object : Callback<List<StoreDTO>> {
            override fun onFailure(call: retrofit2.Call<List<StoreDTO>>, t: Throwable) {
                Log.d("search result retrofit",t.message)
            }

            override fun onResponse(call: retrofit2.Call<List<StoreDTO>>, response: Response<List<StoreDTO>>) {
                if(response.isSuccessful){
                    Log.d("search result retrofit","${response.body()!!}\n")
                    dataInfo(response.body()!! as ArrayList<StoreDTO>)
                    initMarkerLocation()
                }
            }
        })
    }

    fun deletedSRCardView(){
        loc = null
    }

    /* 수정해야할 부분:  1.주변모두 추천장소 데이터 구분(어떤 방식으로 데이터를 결정지은것인가) */
    private fun dataInfo(list: ArrayList<StoreDTO>) { // DataList Set
        when (list.size) { // 수정예정
            in 0..1 -> {
                nearPlaceList = list
                recommendList = list
            }
            else -> {
                nearPlaceList = list
                recommendList = list.reversed() as ArrayList<StoreDTO>
            }
        }
        resultAdapter.setList(nearPlaceList)

        when(tabSelect){
            1 -> mapFragment.getSearchRequestStoreData(nearPlaceList)
            2 -> mapFragment.getSearchRequestStoreData(recommendList)
        }
    }

    private fun initMarkerLocation(){ // 받아온 데이터 들의 위치를 MarkerManager 에 넘김
        locationList.clear()
        when(tabSelect){
            1 ->
                for(item in nearPlaceList)
                    locationList.add(LMMarkerItem(item.latitude,item.longitude))
            2 ->
                for(item in recommendList)
                    locationList.add(LMMarkerItem(item.latitude,item.longitude))
        }
    }

    override fun onSRItemClicked(view: View, position: Int) {
        mapFragment.markerFlag = false
        initMarkerLocation()
        loc = LatLng(locationList[position].lat,locationList[position].lon)
        Log.d("hakjin", "검색결과 클릭$position")

        mainAct.btn_search_result_map.text ="목록"
        cfm.beginTransaction().replace(R.id.fragment_container,mapFragment).addToBackStack(null).commit()
        mapFragment.markerAdd(locationList,true,position)

        mapFragment.setSRSelectedMarker(loc!!)  // 내가추가한것
        mapFragment.setCamera(17.0f,loc!!)
    }

    override fun onBackPressed() {
        mainAct.btn_search_cancle.visibility = View.GONE
        mapFragment.markerDelete()
        when(cfm.backStackEntryCount){
            0 -> {
                mainAct.setOnBackPressedListener(null)
                mainAct.onBackPressed()
            }
            2 -> { // Map 이 아닌상태에서 뒤로가기
                mainAct.sv_searchview.clearFocus()
                mainAct.sv_searchview.setQuery("",false)
                cfm.popBackStack()
                cfm.popBackStack()
                rootView = null
            }
            else -> { // Map 에서 뒤로가기
                changeResultMapState(true)
                mainAct.sv_searchview.setQuery("", false)
                cfm.popBackStack()
                cfm.popBackStack()

                rootView = null
                mapFragment.manageSearchResultCardView(false)
                mapFragment.deleteSRSelectedMarker()
                deletedSRCardView()
            }
        }
    }

    private fun configureRadius(){ // Decide Rendering Range From CameraZoom
        val cameraZoom = mapFragment.getCameraZoom()
        cameraPosition = mapFragment.getCameraPosition()
        when(cameraZoom){
            in 20.0f .. 21.0f -> { // 23m
                mapRadius = 0.023
            }
            in 19.0f .. 20.0f -> { // 47m
                mapRadius = 0.047
            }
            in 18.0f .. 19.0f -> { // 94m
                mapRadius = 0.094
            }
            in 17.0f .. 18.0f -> { // 188M
                mapRadius = 0.188
            }
            in 16.0f .. 17.0f -> { // 375M
                mapRadius = 0.375
            }
            in 15.0f .. 16.0f -> { // 750M
                mapRadius = 0.750
            }
            in 14.0f .. 15.0f -> { // 1500M
                mapRadius = 1.500
            }
            in 13.0f .. 14.0f -> { //3000M
                mapRadius = 3.000
            }
            else -> { // 5KM
                mapRadius = 5.000
            }
        }
    }

    override fun onCreatedViewFinish() {
        if(loc!=null)
            mapFragment.manageSearchResultCardViewData()
    }
}
