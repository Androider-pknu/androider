package com.professionalandroid.apps.androider.search.click

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.professionalandroid.apps.androider.*
import com.professionalandroid.apps.androider.navigation.SearchFragment.Companion.cfm
import com.professionalandroid.apps.androider.navigation.SearchFragment.Companion.mapFragment
import com.professionalandroid.apps.androider.navigation.SearchFragment.Companion.searchOnQueryFlag
import kotlinx.android.synthetic.main.fragment_hot_place.*
import kotlinx.android.synthetic.main.fragment_search.*

class HotPlaceFragment : Fragment(),
    OnBackPressedListener,
    CategoryAdapter.OnCategoryItemClickListener,
    NearHotPlaceAdapter.OnNHPItemClickListener {
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var nearHotPlaceAdapter: NearHotPlaceAdapter
    private lateinit var categoryList: ArrayList<Category>
    private lateinit var nearHotplaceList: ArrayList<NearHotPlace>
    private lateinit var localMasterBtnList: ArrayList<ImageButton>

    private lateinit var mainAct: MainActivity

    override fun onAttach(context: Context) {
        Log.d("hakjin", "HotPlaceFragment onAttach")
        super.onAttach(context)
        mainAct = context as MainActivity
        mainAct.setOnBackPressedListener(this)
    }

    /* onCreate 는 onCreateView 이전에 호출된다.*/
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view : View = inflater.inflate(R.layout.fragment_hot_place,container,false)
        initDataList()
        categoryAdapter = CategoryAdapter(categoryList)
        nearHotPlaceAdapter = NearHotPlaceAdapter(nearHotplaceList)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initLocalBtnList() // onCreateView 에서 하면 오류발생
        recyclerViewApply()
        clickListenerManage()
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
            onBackPressed() // 닫기버튼을 눌렀을떄 onBackPressed 호출
        }
    }

    private fun recyclerViewApply(){
        rv_category?.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            adapter = categoryAdapter
        }
        rv_near_hot_place_list.apply {
            layoutManager = GridLayoutManager(requireContext(),2)
            adapter = nearHotPlaceAdapter
        }
    }

    private fun initLocalBtnList(){ // 동네마스터 버튼 초기화
        localMasterBtnList =
            arrayListOf(imgbtn_local_master1,
            imgbtn_local_master2,
            imgbtn_local_master3,
            imgbtn_local_master4,
            imgbtn_local_master5)
    }

    private fun initDataList(){
        categoryList = ArrayList()
        nearHotplaceList = ArrayList()

        initCategoryList()
        initNearHotPlaceList()
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
    private fun initNearHotPlaceList(){
        for (i in 1..20) nearHotplaceList.add(
            NearHotPlace(
                R.drawable.image03,
                "이학진",
                "유니온 썬 타워"
            )
        )
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
            when(view?.id){
                R.id.imgbtn_local_master1 -> {
                    Toast.makeText(requireContext(), "1등클릭", Toast.LENGTH_LONG).show()

                }
                R.id.imgbtn_local_master2 -> {
                    Toast.makeText(requireContext(), "2등클릭", Toast.LENGTH_LONG).show()

                }
                R.id.imgbtn_local_master3 -> {
                    Toast.makeText(requireContext(), "3등클릭", Toast.LENGTH_LONG).show()

                }
                R.id.imgbtn_local_master4 -> {
                    Toast.makeText(requireContext(), "4등클릭", Toast.LENGTH_LONG).show()

                }
                R.id.imgbtn_local_master5 -> {
                    Toast.makeText(requireContext(), "5등클릭", Toast.LENGTH_LONG).show()

                }
            }
            mapFragment.markerUpdate(true)
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

                mapFragment.markerUpdate(false)
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







