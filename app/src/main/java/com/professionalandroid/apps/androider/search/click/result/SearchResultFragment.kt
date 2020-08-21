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
import com.professionalandroid.apps.androider.navigation.addpost.addressing.ChooseAddressingDialog
import com.professionalandroid.apps.androider.search.map.marker.LMMarkerItem
import com.professionalandroid.apps.androider.util.AWSRetrofit
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.fragment_search_result.*
import kotlinx.android.synthetic.main.fragment_search_result.view.rv_search_result
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

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
    var loc: LatLng? = null // clicked marker position
    var pos: Int = 0 // clicked cardView position
    private var hashMap =  HashMap<String,Int>() // Same Name Store -> Because of PostCount
    private var imgHashMap = HashMap<String,String>()

    override fun onAttach(context: Context) {
        Log.d("SearchResult","SearchResultFragment OnAttach")
        super.onAttach(context)
        tabSelect = 1
        resultAdapter = SearchResultAdapter(context)
        setRadius()
        getStoreData() // GetStoreData
        mainAct = context as MainActivity
        mainAct.setOnBackPressedListener(this)
        mapFragment.setOnMapCreatedViewFinish(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if(rootView==null){
            rootView = inflater.inflate(R.layout.fragment_search_result, container, false)
            mapFragment.setCamera(15.0f,mapFragment.getCameraPosition())
        }
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
        btn_search_selectstore_addstore.setOnClickListener {
            val dialog =
                ChooseAddressingDialog()
            dialog.show(childFragmentManager, "missiles")
        }
    }

    override fun onDetach() {
        loc = null
        super.onDetach()
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
                mapFragment.manageSearchResultCardView(false)
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

    private fun overlapRemove(list: ArrayList<StoreDTO>){ // 중복제거
        if(list.isNotEmpty()) {
            for (index in list.indices) { // Map 에서 키가 중복되면 기존값 덮어씌워짐
                val count = list.count { it.name == list[index].name }
                hashMap[list[index].name] = count
                if(list[index].image_url!=null)
                    imgHashMap[list[index].name] = list[index].image_url!!
            }
        }
        getStoreDataList()
    }

    private fun getStoreImg(list: ArrayList<StoreDTO>){ // Store Img Get
        for(index in list.indices){
            if(imgHashMap[list[index].name]!=null)
                list[index].image_url = imgHashMap[list[index].name]
        }
    }

    private fun countPost(list: ArrayList<StoreDTO>): ArrayList<StoreDTO> { // Store Post Count
        for(item in list){
            if(hashMap[item.name]!=null)
                item.postCount = hashMap[item.name]!!
            else
                item.postCount = 0
        }
        return list
    }

    private fun getStoreData(){ // Get PostCount Information
        val string = arguments?.getString("searchresult").toString()
        val retrofitAPI = AWSRetrofit.getAPI()
        val call = retrofitAPI.getStore(string,mapRadius,cameraPosition.latitude,cameraPosition.longitude)
        call.enqueue(object : Callback<List<StoreDTO>> {
            override fun onFailure(call: retrofit2.Call<List<StoreDTO>>, t: Throwable) {
                Log.d("search result retrofit1",t.message)
            }
            override fun onResponse(call: retrofit2.Call<List<StoreDTO>>, response: Response<List<StoreDTO>>) {
                if(response.isSuccessful){
                    Log.d("search result retrofit1","${response.body()}")
                    overlapRemove(response.body()!! as ArrayList<StoreDTO>)
                }
            }
        })
    }

    private fun getStoreDataList(){ // Get Store Information 거리가 기준
        val string = arguments?.getString("searchresult").toString()
        val retrofitAPI = AWSRetrofit.getAPI()
        val call = retrofitAPI.getStoreList(string,mapRadius,cameraPosition.latitude,cameraPosition.longitude)
            call.enqueue(object : Callback<List<StoreDTO>> {
            override fun onFailure(call: retrofit2.Call<List<StoreDTO>>, t: Throwable) {
                Log.d("search result retrofit2",t.message)
            }

            override fun onResponse(call: retrofit2.Call<List<StoreDTO>>, response: Response<List<StoreDTO>>) {
                if(response.isSuccessful){
                    val dataList = countPost(response.body()!! as ArrayList<StoreDTO>)
                    getStoreImg(dataList)
                    dataInfo(dataList)
                    initMarkerLocation()
                }
            }
        })
    }

    fun deletedSRCardView(){
        loc = null
    }

    private fun dataInfo(list: ArrayList<StoreDTO>) { // DataList Set
        when (list.size) { // 수정예정
            in 0..1 -> {
                nearPlaceList = list
                recommendList = list
            }
            else -> {
                val list2 = list.clone() as ArrayList<StoreDTO>
                nearPlaceList = list
                recommendList = list2
                recommendList.sortWith(Comparator { p0, p1 -> p1.postCount-p0.postCount }) // PostCount Sorted
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
        when (tabSelect) {
            1 ->
                for (item in nearPlaceList) {
                    locationList.add(LMMarkerItem(item.latitude, item.longitude))
                }
            2 ->
                for (item in recommendList) {
                    locationList.add(LMMarkerItem(item.latitude, item.longitude))
                }
        }
    }

    override fun onSRItemClicked(view: View, position: Int) {
        mapFragment.markerFlag = false
        initMarkerLocation()
        circulateCardViewPosition(position)
        loc = LatLng(locationList[position].lat,locationList[position].lon)
        Log.d("hakjin", "검색결과 클릭$position")

        mainAct.btn_search_result_map.text ="목록"
        cfm.beginTransaction().replace(R.id.fragment_container,mapFragment).addToBackStack(null).commit()
        mapFragment.markerAdd(locationList,true,position)

        mapFragment.setSRSelectedMarker(loc!!)  // 내가추가한것
        mapFragment.setCamera(17.0f,loc!!)
    }

    private fun circulateCardViewPosition(position: Int){ // Decision CardView Position
        var count = 0
        for (index in position-1 downTo(0)){
            if(locationList[position].lat == locationList[index].lat && locationList[position].lon == locationList[index].lon){
                count++
            }
        }
        pos = count
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
                mapFragment.deleteSRSelectedMarker()
                deletedSRCardView()
            }
        }
    }

    private fun setRadius(){ // Decide Rendering Range From CameraZoom
        val zoom = mapFragment.getCameraZoom()
        cameraPosition = mapFragment.getCameraPosition()
        mapRadius = OnZoomChangeRadius.changeRadius(zoom)
    }

    override fun onCreatedViewFinish() {
        if(loc!=null)
            mapFragment.manageSearchResultCardViewData(pos)
    }
}
