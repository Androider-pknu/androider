package com.professionalandroid.apps.androider

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
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.professionalandroid.apps.androider.navigation.SearchFragment.Companion.mCurrentCameraLocation
import kotlinx.android.synthetic.main.fragment_main_map.*
import kotlinx.android.synthetic.main.fragment_main_map.view.*
import kotlinx.android.synthetic.main.fragment_main_map.vp_local_master_viewPager

/* 서치결과 맵 마커클릭시 가게정보 뜸 동네마스터 마커 클릭시 해당사람이 쓴 포스트 뜸*/

class MainMapFragment() : Fragment(), OnMapReadyCallback, GoogleMap.OnCameraIdleListener,
    OnBackPressedListener,
    GoogleMap.OnMarkerClickListener, GoogleMap.OnMapClickListener {

    lateinit var mMap: GoogleMap
    lateinit var mFusedLocationClient: FusedLocationProviderClient
    private val REQUEST_CODE_PERMISSIONS = 1000
    private var idleCameraCheck = true

    private lateinit var markerRootView: View
    private lateinit var btnMarker: Button

    private lateinit var btnList: Array<Button>
    private var btnFlag = false
    private var markerLocationList = ArrayList<LatLng>()

    /* posting 을 표시할 list */
    private var restaurantList =  ArrayList<LatLng>()
    private var cafeList = ArrayList<LatLng>()
    private var alcoholList = ArrayList<LatLng>()
    private var cultureList = ArrayList<LatLng>()
    private var lifeList = ArrayList<LatLng>()

    private lateinit var localMasterAdapter: LMMarkerAdapter
    private lateinit var searchResultPageAdapter: SearchResultPageAdapter

    private var markerList =  ArrayList<Marker>()
    var markerFlag = true // 동네마스터 마커인지 검색결과 마커인지 판별 true: 동네마스터 마커 false 검색결과  searchFragment, hotplaceFramgent 에서 설정

    /* 현재위치 정보 저장을 위한 boolean true 이면 핸재위치로 업데이트 false 이면 camera 위치로 update
    * 즉 camera 가 멈춘후 다른 프래그먼트를 호출했을때 그 카메라 줌(위치) 상태를 저장하기 위한 친구 */

    override fun onAttach(context: Context) {
        super.onAttach(context)
        initMarkerAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_main_map, container, false)
        Log.d("hakjin", "Map onCreateView")

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

    private fun changeVisibleBtn(){
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

    override fun onResume() {
        super.onResume()
        Log.d("hakjin", "Map onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("hakjin", "Map onPause")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("hakjin", "Map onDestroy")
    }

    /* onMapReady 는 비동기 방식으로 작동 매개변수로 구글맵 전달*/
    override fun onMapReady(googleMap: GoogleMap?) {
        googleMap?.let {
            Log.d("hakjin", "Map onMapReady")
            mMap = it
            mMapClickListenerRegister()
            initLocation()
        }
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
        mMap.setOnMarkerClickListener(this)
        mMap.setOnMapClickListener(this)
    }

    private fun initLocation() {    //내위치를 마커로 찍음
        mFusedLocationClient = FusedLocationProviderClient(requireContext())
        mMap.isMyLocationEnabled = true // 파란색점
        mMap.uiSettings.isMyLocationButtonEnabled = false // 내위치 버튼 비활성화
        updateLocation()
    }

    private fun updateLocation() { // 위치 업데이트
        mFusedLocationClient.lastLocation.addOnSuccessListener {
            val myLocation = LatLng(it.latitude, it.longitude)
            /* cameraCheck is true 이면 현재위치로 카메라로 이동 false 이면 내위치로 camera 가 이동될 필요가 없음 */
            when (idleCameraCheck) {
                true -> {
                    mCurrentCameraLocation = myLocation
                    idleCameraCheck = false
                }
            }
            mMap.moveCamera(CameraUpdateFactory.newLatLng(mCurrentCameraLocation))
            mMap.animateCamera(CameraUpdateFactory.zoomTo(17.0f))
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

    override fun onCameraIdle() {
        mCurrentCameraLocation = mMap.cameraPosition.target
    }

    override fun onBackPressed() {
        childFragmentManager.popBackStack()
    }

    override fun onMarkerClick(marker: Marker?): Boolean { // true 동네마스터 마커 false 검색결과 마커 ( 같은마커 안찍는다고 가정)
        when(markerFlag){
            true -> {
                Log.d("hakjin", "동네마스터 마커 클릭")
                manageLocalCardViewData(marker) // 마커객체 넘겨서 카드뷰 업데이트 (동일마커 클릭 고려 X)
                manageLocalMasterCardView(true) // 카드뷰 가시성 관리
            }
            false -> {
                Log.d("hakjin","검색 결과 마커 클릭")
                manageSearchResultCardViewData(marker) // 마커객체 넘겨서 카드뷰 업데이트 (동일마커 클릭 고려 X)
                manageSearchResultCardView(true) // 카드뷰 가시성 관리
            }
        }
        //markerShapeChange(marker)
        return true
    }

    private fun markerShapeChange(marker: Marker?){ // 클릭시 마커 확대
        val position = marker?.position
        MarkerOptions().position(position!!).icon
        (BitmapDescriptorFactory.fromBitmap(resources.getDrawable(R.drawable.restaurant).toBitmap(70, 70)))
    }

    override fun onMapClick(p0: LatLng?) { // 찍힌 LatLng 넘어옴
        Log.d("hakjin", "맵클릭")
        if(markerFlag) manageLocalMasterCardView(false)
        else manageSearchResultCardView(false)
    }

    fun markerUpdate(flag: Boolean){
        when(flag){
            true -> initMarkerList()
            false -> for(i in markerList) i .remove()
        }
    }

    private fun initMarkerList(){
        val list = ArrayList<LatLng>() //  마커의 위치 리스트
        for (i in 1..20) list.add(LatLng(35.113 + (i * 0.001), 129.113))
        markerLocationList = list
        markerAdd()
    }

    private fun markerAdd(){
        // 동네마스터 마커: -> 5개의 종류 검색결과 마커: -> 12개의 카테고리 (case 분류)
        for(pos in markerLocationList){
            markerList.add(
                mMap.addMarker(
                    MarkerOptions().position(pos).title("----가게이름---").snippet("테스트 데이터입니다.").icon(
                        BitmapDescriptorFactory.fromBitmap(
                            resources.getDrawable(R.drawable.restaurant).toBitmap(50, 50)
                        )
                    )
                )
            )
        }
    }

    private fun addSearchResultMarkerModel(){ // 마커에 담길 카드뷰 데이터 추가
        Log.d("hakjin","addSearchResultMarkerModel 호출")
        searchResultPageAdapter.addItem(
            SearchResultMarkerModel(R.drawable.image03,"잭슨바","바",
        "051-611-4608","부산광역시 남구 대연동 52-18 대영빌딩")
        )
        searchResultPageAdapter.addItem(SearchResultMarkerModel(R.drawable.image03,"학진바","바",
            "051-611-4608","부산광역시 남구 대연동 52-18 대영빌딩"))
        searchResultPageAdapter.notifyDataSetChanged()
    }

    private fun addLocalMasterMarkerModel() { // 동네마스터 마커데이터 초기화
        Log.d("hakjin","addLocalMasterMarkerModel 호출")
        localMasterAdapter.addItem(LocalMasterMarkerModel(R.drawable.image03,R.drawable.image03, "하하하하하하하하하하하하하하하하하하하하하하하하하하하하하하하하하하하하하하하하하하하하하하하하하하하하하하하하하하하하하", "아관파천"))
        localMasterAdapter.addItem(
            LocalMasterMarkerModel(
                R.drawable.image03, R.drawable.image02,
                "하하하하하하하하하하하하하하하하하하하하하하하하하하하하하하하하하하하하하하하하하하하하하하하하하하하하하하하하하하하", "동서남북"
            )
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
        Log.d("hakjin","manageSearchResultCardViewData 호출")
        searchResultPageAdapter.removeItem() // 현재 어댑터에 있는 아이템 제거
        addSearchResultMarkerModel()        // 마커에 달릴 카드뷰에 데이터 추가
    }

    private fun manageLocalCardViewData(marker: Marker?) { // 동네마스터 마커 카드뷰 데이터 관리
        Log.d("hakjin","manageLocalCardViewData 호출")
        localMasterAdapter.removeItem() // 이전데이터 삭제 -> 이비분 문제인듯 localMasterMarkerModel은  빈놈
        addLocalMasterMarkerModel()
        //test()
    }

    /* Custom Marker 작업중 미완성 */
//    private fun getMarkerItem(){
//        val list = ArrayList<LatLng>() //  마커의 위치 리스트
//        for (i in 1..20) list.add(LatLng(35.113 + (i * 0.001), 129.113))
//
//        for(i in list) addMarker(i, false)
//    }
//
//    private fun setCustomMarkerView(){
//        markerRootView = LayoutInflater.from(requireContext()).inflate(R.layout.localmaster_marker,null)
//        btnMarker = markerRootView.findViewById(R.id.btn_lm_marker)
//    }
//
//    private fun addMarker(markerItem: LMMarkerItem, isSelectedMarker: Boolean): Marker{
//
//
//        return marker
//    }
}




















