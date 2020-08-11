package com.professionalandroid.apps.androider.search.map

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.professionalandroid.apps.androider.*
import com.professionalandroid.apps.androider.search.click.result.SearchResultPageAdapter
import com.professionalandroid.apps.androider.search.map.marker.*
import kotlinx.android.synthetic.main.fragment_main_map.*
import kotlinx.android.synthetic.main.fragment_main_map.view.*
import kotlinx.android.synthetic.main.fragment_main_map.vp_local_master_viewPager

/* 서치결과 맵 마커클릭시 가게정보 뜸 동네마스터 마커 클릭시 해당사람이 쓴 포스트 뜸*/

class MainMapFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnCameraIdleListener,
    GoogleMap.OnCameraMoveListener, GoogleMap.OnMarkerClickListener, GoogleMap.OnMapClickListener {

    private lateinit var mMap: GoogleMap
    private lateinit var mFusedLocationClient: FusedLocationProviderClient
    private val REQUEST_CODE_PERMISSIONS = 1000
    private lateinit var mainActivity: MainActivity

    private lateinit var cameraPosition: LatLng
    private var lmMarkerManager : LMMarkerManager? = null
    private var cameraZoom: Float = 0.0f

    private lateinit var btnList: Array<Button>
    private lateinit var localMasterAdapter: LMMarkerAdapter
    private lateinit var searchResultPageAdapter: SearchResultPageAdapter

    var markerFlag = true // true: 동네마스터 마커 false: 검색결과
    private var idleCameraCheck = true // MyLocation Update
    private var btnFlag = false // Button Flag

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
        cameraZoom = 16.0f
        initMarkerAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_main_map, container, false)
        Log.d("Map", "Map onCreateView")

        setLocalMasterMarkerAdapter(rootView) // 뷰페이저에 마커어댑터 설정
        initBtnList(rootView)

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapFragment: SupportMapFragment? =
            childFragmentManager.findFragmentById(R.id.Main_map_Fragment) as SupportMapFragment
        mapFragment?.getMapAsync(this)

        btn_my_location.setOnClickListener {
            idleCameraCheck = true // 내위치 업데이트를 위해 true
            updateLocation()
        }

        btn_filter.setOnClickListener {
            changeVisibleBtn()
        }
    }

    private fun initBtnList(view: View){
        btnList = arrayOf(view.btn_search_culture,view.btn_search_life,view.btn_search_restautrant,view.btn_search_cafe,view.btn_search_alcohol)
    }

    private fun changeVisibleBtn(){ // 필터버튼 ( Inner 5걔)
        when(btnFlag){
            false ->{
                for (i in btnList) i.visibility = View.VISIBLE
                btnFlag = true
            }
            true ->{
                for (i in btnList) i.visibility = View.GONE
                btnFlag = false
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        googleMap?.let {
            mMap = it
            mMapClickListenerRegister()
            initLocation()
            if(lmMarkerManager==null){
                lmMarkerManager = LMMarkerManager(requireContext(),mMap)
            }
        }
    }

    fun getCameraZoom(): Float{ // 현재 카메라 줌을 반환
        return cameraZoom
    }

    fun setCamera(zoom: Float,position: LatLng){
        cameraZoom = zoom
        mMap.moveCamera(CameraUpdateFactory.newLatLng(position))
        mMap.animateCamera(CameraUpdateFactory.zoomTo(cameraZoom))
    }

    fun getCameraPosition(): LatLng{
        return cameraPosition
    }

    /* 마커, 맵 클릭리스너 시작 */
    private fun initMarkerAdapter(){ // 마커 초기화, 데이터 설정
        localMasterAdapter = LMMarkerAdapter(requireContext())
        addLocalMasterMarkerModel()

        searchResultPageAdapter = SearchResultPageAdapter(requireContext())
        addSearchResultMarkerModel()
    }

    private fun mMapClickListenerRegister() { // 맵클릭 리스너 등록
        mMap.setOnCameraIdleListener(this)
        mMap.setOnCameraMoveListener(this)
        mMap.setOnMarkerClickListener(this)
        mMap.setOnMapClickListener(this)
    }

    private fun initLocation() {    //내위치를 마커로 찍음
        mFusedLocationClient = FusedLocationProviderClient(requireContext())
        mMap.isMyLocationEnabled = true
        mMap.uiSettings.isMyLocationButtonEnabled = false
        updateLocation()
    }

    private fun updateLocation() { // 위치 업데이트
        mFusedLocationClient.lastLocation.addOnSuccessListener {
            val myLocation = LatLng(it.latitude, it.longitude)
            when (idleCameraCheck) {
                true -> {
                    cameraPosition = myLocation
                    idleCameraCheck = false
                    cameraZoom = 16.0f
                }
            }
            mMap.moveCamera(CameraUpdateFactory.newLatLng(cameraPosition))
            mMap.animateCamera(CameraUpdateFactory.zoomTo(cameraZoom))
            idleCameraCheck = false
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                Toast.makeText(requireContext(), "위치권한 체크 거부 됨", Toast.LENGTH_SHORT).show()
            } else {
                // success# do something...
            }
        }
    }

    override fun onCameraMove() {
        cameraPosition = mMap.cameraPosition.target
    }

    override fun onCameraIdle() {
        cameraPosition = mMap.cameraPosition.target
        cameraZoom = mMap.cameraPosition.zoom
    }

    override fun onMarkerClick(marker: Marker?): Boolean { // true 동네마스터 마커 false 검색결과 마커
        Log.d("map22","onMarkerClick")
        mMap.moveCamera(CameraUpdateFactory.newLatLng(marker?.position))
        when(markerFlag){
            true -> {
                Log.d("marker", "동네마스터 마커 클릭")
                manageLocalCardViewData(marker) // 마커객체 넘겨서 카드뷰 업데이트
                manageLocalMasterCardView(true)
            }
            false -> {
                Log.d("marker","검색 결과 마커 클릭")
                manageSearchResultCardViewData(marker)
                manageSearchResultCardView(true)
            }
        }
        lmMarkerManager?.changedSelectedMarker(marker) // 선택한 마커 표시
        return true
    }

    override fun onMapClick(p0: LatLng?) {
        Log.d("map22","onMapClick")
        lmMarkerManager?.changedSelectedMarker(null)
        if(markerFlag) manageLocalMasterCardView(false)
        else manageSearchResultCardView(false)
    }

    fun markerAdd(markerList: ArrayList<LMMarkerItem>, flag: Boolean, position: Int){ // flag: true -> Selected Marker O
        if(flag)
            lmMarkerManager?.getMarkerItem(markerList,position)
        else
            lmMarkerManager?.getMarkerItem(markerList)
    }

    fun markerDelete(){
        lmMarkerManager?.deleteMarker()
    }

    private fun addSearchResultMarkerModel(){ // 마커에 담길 카드뷰 데이터 추가
        searchResultPageAdapter.addItem(
            SearchResultMarkerModel(R.drawable.image03, "잭슨바", "바", "051-611-4608", "부산광역시 남구 대연동 52-18 대영빌딩")
        )
        searchResultPageAdapter.addItem(
            SearchResultMarkerModel(R.drawable.image03, "학진바", "바", "051-611-4608", "부산광역시 남구 대연동 52-18 대영빌딩")
        )
        searchResultPageAdapter.notifyDataSetChanged()
    }

    private fun addLocalMasterMarkerModel() { // 동네마스터 마커데이터 초기화
        localMasterAdapter.addItem(
            LocalMasterMarkerModel(R.drawable.image03, R.drawable.image03, "하하하하하하하하하하하하하하하하하하하하하하하하하하하하하하하하하하하하하하하하하하하하하하하하하하하하하하하하하하하하하", "아관파천")
        )
        localMasterAdapter.addItem(
            LocalMasterMarkerModel(R.drawable.image03, R.drawable.image02, "하하하하하하하하하하하하하하하하하하하하하하하하하하하하하하하하하하하하하하하하하하하하하하하하하하하하하하하하하하하", "동서남북")
        )
        localMasterAdapter.notifyDataSetChanged()
    }
    private fun setLocalMasterMarkerAdapter(view: View) { // 동네마스터 마커, 검색결과 마커 뷰페이저 어댑터 설정
        // 동네마스터 marker 어댑터 설정
        view.vp_local_master_viewPager.adapter = localMasterAdapter
        view.vp_local_master_viewPager.setPadding(50, 0, 50, 50)
        view.vp_local_master_viewPager.pageMargin = 30
        //  서치리절트 marker 어댑터 설정
        view.vp_searchresult_viewPager.adapter = searchResultPageAdapter
        view.vp_searchresult_viewPager.setPadding(50, 0, 50, 50)
    }

    private fun manageLocalMasterCardView(flag: Boolean) { // true -> 보임 false -> 안보임
        when (flag) {
            true -> vp_local_master_viewPager.visibility = View.VISIBLE
            false -> vp_local_master_viewPager.visibility = View.GONE
        }
    }

    private fun manageSearchResultCardView(flag: Boolean) { // true -> 보임 false -> 안보임
        when (flag) {
            true -> vp_searchresult_viewPager.visibility = View.VISIBLE
            false -> vp_searchresult_viewPager.visibility = View.GONE
        }
    }

    private fun manageSearchResultCardViewData(marker: Marker?){ // 검색결과 마커 카드뷰 데이터 관리
        searchResultPageAdapter.removeItem()
        addSearchResultMarkerModel()
    }

    private fun manageLocalCardViewData(marker: Marker?) { // 동네마스터 마커 카드뷰 데이터 관리
        localMasterAdapter.removeItem()
        addLocalMasterMarkerModel()
    }
}



















