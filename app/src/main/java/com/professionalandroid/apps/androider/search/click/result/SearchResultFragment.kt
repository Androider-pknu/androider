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
import com.professionalandroid.apps.androider.R
import com.professionalandroid.apps.androider.model.StoreDTO
import com.professionalandroid.apps.androider.navigation.SearchFragment.Companion.cfm
import com.professionalandroid.apps.androider.navigation.SearchFragment.Companion.mapFragment
import com.professionalandroid.apps.androider.util.AWSRetrofit
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.fragment_search_result.*
import kotlinx.android.synthetic.main.fragment_search_result.view.rv_search_result
import retrofit2.Callback
import retrofit2.Response

class SearchResultFragment : Fragment(),
    OnBackPressedListener, SearchResultAdapter.OnSRItemClickListener {

    private lateinit var resultAdapter: SearchResultAdapter
    private lateinit var mainAct: MainActivity

    private var recommendList = ArrayList<StoreDTO>()
    private var nearPlaceList = ArrayList<StoreDTO>()
    private var cameraZoom = 0.0f // Current Camera Zoom
    private var mapRadius: Double = 0.0 // mapRadius EX) 14.0F -> 3KM
    private lateinit var cameraPosition: LatLng

    override fun onAttach(context: Context) {
        Log.d("hakjin","SearchResultFragment OnAttach")
        super.onAttach(context)
        resultAdapter = SearchResultAdapter()
        configureRadius()
        getStoreData()
        mainAct = context as MainActivity
        mainAct.setOnBackPressedListener(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search_result, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setResultAdapter()
        manageTabLayout()
        clickListenerMange()
    }

    private fun clickListenerMange(){
        resultAdapter.setOnSRClickListener(this)
    }

    private fun manageTabLayout(){
        search_tab_layout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabReselected(tab: TabLayout.Tab?) { /* adapter 에 넣을 리스트를 초기화 0 -> 주변장소 1 -> 추천장소 */
                when (tab?.position){
                    0 -> resultAdapter.setList(nearPlaceList)
                    1 -> resultAdapter.setList(recommendList)
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position){
                    0 -> resultAdapter.setList(nearPlaceList)
                    1 -> resultAdapter.setList(recommendList)
                }
            }
        })
    }

    private fun setResultAdapter() { // Adapter Set
        rv_search_result.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            setHasFixedSize(true)
            rv_search_result.adapter = resultAdapter
        }
    }
    override fun onBackPressed() {
        mainAct.btn_search_cancle.visibility = View.GONE
        mapFragment.markerUpdate(false)
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
    }

    private fun getStoreData(){ // Store Information Get
        val string = arguments?.getString("searchresult").toString()
        val retrofitAPI = AWSRetrofit.getAPI()
        val call = retrofitAPI.getStore(string,mapRadius,cameraPosition.latitude,cameraPosition.longitude)
            call.enqueue(object : Callback<List<StoreDTO>> {
            override fun onFailure(call: retrofit2.Call<List<StoreDTO>>, t: Throwable) {
                Log.d("retrofit",t.message)
            }

            override fun onResponse(call: retrofit2.Call<List<StoreDTO>>, response: Response<List<StoreDTO>>) {
                if(response.isSuccessful){
                    Log.d("retrofit","${response.body()!!}\n")
                    dataInfo(response.body()!! as ArrayList<StoreDTO>)
                }
            }
        })
    }

    /* 수정해야할 부분:  1.주변모두 추천장소 데이터 구분(어떤 방식으로 데이터를 결정지은것인가) */
    private fun dataInfo(list: ArrayList<StoreDTO>) { // DataList Set
        when (list.size) { // 수정예정
            in 0..2 -> {
                nearPlaceList = list
                recommendList = list
            }
            else -> {
                nearPlaceList = list
                recommendList = list.reversed() as ArrayList<StoreDTO>
            }
        }
        resultAdapter.setList(nearPlaceList)
    }

    private fun configureRadius(){ // Rendering Range decide from cameraZoom
        cameraZoom = mapFragment.getCameraZoom()
        cameraPosition = mapFragment.getCameraPosition()
        Log.d("map22","$mapRadius")
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

    override fun onSRItemClicked(view: View, position: Int) {
        Log.d("hakjin", "검색결과 클릭$position")
        cfm.beginTransaction().replace(R.id.fragment_container, mapFragment).addToBackStack(null).commit()
    }
}
