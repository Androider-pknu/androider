package com.professionalandroid.apps.androider.search.click

import android.content.Context
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.gms.maps.model.LatLng
import com.professionalandroid.apps.androider.*
import com.professionalandroid.apps.androider.model.MemberDTO
import com.professionalandroid.apps.androider.model.StoreDTO
import com.professionalandroid.apps.androider.navigation.SearchFragment.Companion.cfm
import com.professionalandroid.apps.androider.navigation.SearchFragment.Companion.mapFragment
import com.professionalandroid.apps.androider.navigation.SearchFragment.Companion.searchOnQueryFlag
import com.professionalandroid.apps.androider.search.click.result.OnZoomChangeRadius
import com.professionalandroid.apps.androider.search.map.marker.LMMarkerItem
import com.professionalandroid.apps.androider.util.AWSRetrofit
import kotlinx.android.synthetic.main.fragment_hot_place.*
import kotlinx.android.synthetic.main.fragment_hot_place.view.*
import kotlinx.android.synthetic.main.fragment_search.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.roundToInt

class HotPlaceFragment : Fragment(),
    OnBackPressedListener,
    CategoryAdapter.OnCategoryItemClickListener,
    NearHotPlaceAdapter.OnNHPItemClickListener {
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var nearHotPlaceAdapter: NearHotPlaceAdapter
    private lateinit var categoryList: ArrayList<Category>
    private lateinit var localMasterBtnList: ArrayList<ImageView>
    private var localUsernameList = ArrayList<TextView>()

    private lateinit var mainAct: MainActivity
    private lateinit var con: Context
    private var address: String? = null // 주소
    private var mapRange: Double = 0.0 // 맵 범위
    private var mapRadius: Double = 0.0 // 맵 검색범위
    private lateinit var cameraPosition: LatLng
    private var adminArea: String? = null // EX) 부산광역시 OR 경상남도

    private var nearHotPlaceList =  ArrayList<StoreDTO>() // NearPlace List
    private var hashMap =  HashMap<String,Int>() // Same Name Store -> Because of PostCount
    private var imgHashMap = HashMap<String,String>()

    private var localUserList = ArrayList<MemberDTO>() // Top LocalUserList

    override fun onAttach(context: Context) {
        Log.d("hotPlaceFragment", "HotPlaceFragment onAttach")
        super.onAttach(context)
        mainAct = context as MainActivity
        mainAct.setOnBackPressedListener(this)
        con = context

        cameraPosition = mapFragment.getCameraPosition()
        val zoomRange = OnZoomChangeRadius.changeRadius(mapFragment.getCameraZoom())
        mapRadius = zoomRange
        mapRange = zoomRange * 1000.0
        address = mapFragment.getAddress()
        if(address==null) {
            address = "알수없음"
            adminArea = null
        }
        else{
            adminArea = address?.split(" ")?.get(0)
        }
        nearHotPlaceAdapter = NearHotPlaceAdapter(context,address!!)
        getStoreData()
    }

    /* onCreate 는 onCreateView 이전에 호출된다.*/
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view : View = inflater.inflate(R.layout.fragment_hot_place,container,false)
        initDataList()
        categoryAdapter = CategoryAdapter(categoryList)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getLocalUserInfo()
        view.tv_hotPlace_range.text = "$address 반경 ${decideUnit(mapRange)} 내 인기장소"
        initLocalBtnList() // onCreateView 에서 하면 오류발생
        recyclerViewApply()
        clickListenerManage()
    }

    private fun decideUnit(dis: Double): String{ // 단위 결정
        return if(dis < 1000.0)
            (dis.toInt()).toString()+"m"
        else
            (((dis / 1000.0) * 10).roundToInt() /10).toString()+"Km"
    }

    private fun clickListenerManage(){
        categoryAdapter.setOnClickListener(this) // categoryListener 등록
        // 동네 마스터 클릭리스너 등록
        imgbtn_local_master1.setOnClickListener(LocalMasterClickListener())
        imgbtn_local_master2.setOnClickListener(LocalMasterClickListener())
        imgbtn_local_master3.setOnClickListener(LocalMasterClickListener())
        imgbtn_local_master4.setOnClickListener(LocalMasterClickListener())
        imgbtn_local_master5.setOnClickListener(LocalMasterClickListener())

        nearHotPlaceAdapter.setOnNHPClickListener(this)

        localMasterMapBack()
    }

    private fun localMasterMapBack(){
        mainAct.btn_localMaster_player_cancle.setOnClickListener {
            changeStateMap(false)
            onBackPressed()
        }
    }

    private fun recyclerViewApply(){
        rv_category?.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            adapter = categoryAdapter
        }
        rv_near_hot_place_list.apply {
            layoutManager = GridLayoutManager(requireContext(),2)
            setHasFixedSize(true)
            rv_near_hot_place_list.adapter = nearHotPlaceAdapter
        }
    }

    private fun initLocalBtnList(){ // 동네마스터 버튼 초기화
        localMasterBtnList =
            arrayListOf(imgbtn_local_master1,
            imgbtn_local_master2,
            imgbtn_local_master3,
            imgbtn_local_master4,
            imgbtn_local_master5)
        localUsernameList =
            arrayListOf(tv_local_master1,
            tv_local_master2,
            tv_local_master3,
            tv_local_master4,
            tv_local_master5)
    }

    private fun initDataList(){
        categoryList = ArrayList()
        initCategoryList()
    }
    private fun initCategoryList(){
        categoryList.add(Category("음식점"))
        categoryList.add(Category("카페"))
        categoryList.add(Category("술집"))
        categoryList.add(Category("한식"))
        categoryList.add(Category("일식"))
        categoryList.add(Category("중식"))
        categoryList.add(Category("양식"))
        categoryList.add(Category("분식"))
        categoryList.add(Category("고깃집"))
        categoryList.add(Category("해산물"))
        categoryList.add(Category("디저트"))
        categoryList.add(Category("베이커리"))
    }

    private fun getLocalUserInfo(){ // Get LocalUser
        val retrofitAPI = AWSRetrofit.getAPI()
        when(adminArea){
            null -> {
                Log.d("HotPlaceFragment","No exist LocalUser")
                view?.constraintLayout2?.visibility = View.GONE
                view?.view_below?.visibility = View.GONE
            }
            else -> {
                val call = retrofitAPI.getUserInfo(adminArea!!)
                call.enqueue(object : Callback<List<MemberDTO>>{
                    override fun onFailure(call: Call<List<MemberDTO>>, t: Throwable) {
                        Log.d("localUser Retrofit", " On Failed")
                    }
                    override fun onResponse(call: Call<List<MemberDTO>>, response: Response<List<MemberDTO>>) {
                        if(response.isSuccessful){
                            Log.d("localUser retrofit","${response.body()}")
                            if(response.body()!!.isNotEmpty()) {
                                setLocalUserList(response.body()!! as ArrayList<MemberDTO>)
                            }
                            else{
                                view?.constraintLayout2?.visibility = View.GONE
                                view?.view_below?.visibility = View.GONE
                            }
                        }
                    }
                })
            }
        }
    }

    private fun setLocalUserList(list: ArrayList<MemberDTO>){ // Set LocalUser
        val list2 = list.clone() as ArrayList<MemberDTO>
        localUserList = list2
        localUserList.sortWith(Comparator { p0, p1 -> p1.postCount-p0.postCount }) // PostCount Sorted

        for (index in localUserList.indices) {
            localUsernameList[index].text = localUserList[index].name
            val img = localUserList[index].image_url
            if (img == null) {
                localMasterBtnList[index].setImageResource(R.drawable.image03) // basic Img
            } else {
                Glide.with(requireContext()).load(img).into(localMasterBtnList[index])
                localMasterBtnList[index].background = ShapeDrawable(OvalShape())
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    localMasterBtnList[index].clipToOutline = true
                }
            }
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
        val retrofitAPI = AWSRetrofit.getAPI()
        val call = retrofitAPI.getStoreInfo(mapRadius,cameraPosition.latitude,cameraPosition.longitude)
        call.enqueue(object : Callback<List<StoreDTO>> {
            override fun onFailure(call: retrofit2.Call<List<StoreDTO>>, t: Throwable) {
                Log.d("hotplace retrofit1",t.message)
            }
            override fun onResponse(call: retrofit2.Call<List<StoreDTO>>, response: Response<List<StoreDTO>>) {
                if(response.isSuccessful){
                    Log.d("hotplace retrofit1","${response.body()}")
                    overlapRemove(response.body()!! as ArrayList<StoreDTO>)
                }
            }
        })
    }

    private fun getStoreDataList(){ // Get Store Information
        val retrofitAPI = AWSRetrofit.getAPI()
        val call = retrofitAPI.getStoreList(mapRadius,cameraPosition.latitude,cameraPosition.longitude)
        call.enqueue(object : Callback<List<StoreDTO>> {
            override fun onFailure(call: retrofit2.Call<List<StoreDTO>>, t: Throwable) {
                Log.d("hotplace retrofit2",t.message)
            }

            override fun onResponse(call: retrofit2.Call<List<StoreDTO>>, response: Response<List<StoreDTO>>) {
                if(response.isSuccessful){
                    Log.d("hotplace retrofit2","${response.body()}")
                    val dataList = countPost(response.body()!! as ArrayList<StoreDTO>)
                    getStoreImg(dataList)
                    setStoreList(dataList)
                }
            }
        })
    }

    private fun setStoreList(list: ArrayList<StoreDTO>){
        val list2 = list.clone() as ArrayList<StoreDTO>
        nearHotPlaceList = list2
        nearHotPlaceList.sortWith(Comparator { p0, p1 -> p1.postCount-p0.postCount }) // PostCount Sorted
        nearHotPlaceAdapter.setList(nearHotPlaceList)
    }

    override fun onCategoryItemClicked(view: View, position: Int) {
        searchOnQueryFlag = false
        when(position){
            0 -> {
                mainAct.sv_searchview.setQuery("음식점",true)
            } 1 ->{
                mainAct.sv_searchview.setQuery("카페",true)
            } 2 ->{
                mainAct.sv_searchview.setQuery("술집",true)
            } 3 ->{
                mainAct.sv_searchview.setQuery("한식",true)
            } 4 ->{
                mainAct.sv_searchview.setQuery("일식",true)
            } 5 ->{
                mainAct.sv_searchview.setQuery("중식",true)
            } 6 ->{
                mainAct.sv_searchview.setQuery("양식",true)
            } 7 ->{
                mainAct.sv_searchview.setQuery("분식",true)
            } 8 ->{
                mainAct.sv_searchview.setQuery("고깃집",true)
            } 9 ->{
                mainAct.sv_searchview.setQuery("해산물",true)
            } 10 ->{
                mainAct.sv_searchview.setQuery("디저트",true)
            } 11 ->{
                mainAct.sv_searchview.setQuery("베이커리",true)
            }
        }
    }

    inner class LocalMasterClickListener: View.OnClickListener{
        override fun onClick(view: View?) {
            var position = 0
            when(view?.id){
                R.id.imgbtn_local_master1 -> {

                }
                R.id.imgbtn_local_master2 -> {
                    position=1
                }
                R.id.imgbtn_local_master3 -> {
                    position=2
                }
                R.id.imgbtn_local_master4 -> {
                    position=3
                }
                R.id.imgbtn_local_master5 -> {
                    position=4
                }
            }
            mainAct.tv_player_posting_count.text=
                "${localUserList[position].name} 님의 포스팅 장소 (${localUserList[position].postCount.toString()}개)"

            val list = ArrayList<LMMarkerItem>() // 수정예정
            mapFragment.markerAdd(list,false,-1)

            //mapFragment.markerUpdate(true) -> Real

            mainAct.sv_searchview.clearFocus()
            cfm.beginTransaction().setCustomAnimations(
                R.anim.enter_from_right,
                R.anim.exit_to_left,
                R.anim.enter_from_left,
                R.anim.exit_to_right
            ).replace(R.id.fragment_container, mapFragment) // 카메라의 위치를
                .addToBackStack(null).commit()
            changeStateMap(true) // 맵이동후 textView, btn 보이게함
        }
    }

    private fun changeStateMap(flag : Boolean){ // localMaster 눌렀을때 flag 가 true 나갔을때 flag 는 false
        mapFragment.markerFlag = true // 현재 동네마스터 마커가 사용중이다
        when(flag){
            true -> {
                mainAct.tv_player_posting_count.visibility = View.VISIBLE
                mainAct.btn_localMaster_player_cancle.visibility = View.VISIBLE
                mainAct.sv_searchview.visibility = View.GONE
                mainAct.btn_search_cancle.visibility = View.GONE
            }
            false -> {
                mainAct.tv_player_posting_count.visibility = View.GONE
                mainAct.btn_localMaster_player_cancle.visibility = View.GONE
                mainAct.sv_searchview.visibility = View.VISIBLE
                mainAct.btn_search_cancle.visibility = View.VISIBLE

                //mapFragment.markerUpdate(false)
                mapFragment.markerDelete()
            }
        }
    }

    override fun onBackPressed() {
        when(cfm.backStackEntryCount){
            0 -> {
                mainAct.setOnBackPressedListener(null)
                mainAct.onBackPressed()
            }
            1 -> { // 맵 X 인상태에서 뒤로가기
                mainAct.sv_searchview.clearFocus()
                nearHotPlaceList.clear()
                cfm.popBackStack()
                changeStateMap(false)
                mainAct.btn_search_cancle.visibility = View.GONE
            }
            else -> { // 동네마스터 맵에서 뒤로가기
                mainAct.sv_searchview.clearFocus()
                cfm.popBackStack()
                changeStateMap(false)
            }
        }
    }
    override fun onNHPItemClicked(view: View, position: Int) { //주변 인기 장소
        Log.d("hakjin"," HotPlaceFragment 주변 인기장소 클릭 $position")
    }
}







