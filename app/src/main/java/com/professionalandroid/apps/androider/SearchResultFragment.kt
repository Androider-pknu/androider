package com.professionalandroid.apps.androider

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import com.professionalandroid.apps.androider.navigation.SearchFragment.Companion.mapFragment
import kotlinx.android.synthetic.main.fragment_search_result.*
import kotlinx.android.synthetic.main.fragment_search_result.view.rv_search_result

class SearchResultFragment : Fragment(), OnBackPressedListener, SearchResultAdapter.OnSRItemClickListener {
    private lateinit var resultAdapter: SearchResultAdapter
    private lateinit var mainAct: MainActivity
    private lateinit var cfm: FragmentManager

    override fun onAttach(context: Context) {
        Log.d("hakjin","SearchResultFragment OnAttach")
        super.onAttach(context)
        cfm = childFragmentManager
        mainAct = context as MainActivity
        mainAct.setOnBackPressedListener(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d("hakjin","SearchResultFragment onCreateView")
        return inflater.inflate(R.layout.fragment_search_result, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("hakjin","SearchResultFragment  onViewCreated")
        initSearchResultList(0)
        changeResultData()
        manageTabLayout()
        clickListenerMange()
    }

    override fun onResume() {
        Log.d("hakjin","SearchResultFragment onResume()")
        super.onResume()
    }

    override fun onPause() {
        Log.d("hakjin","SearchResultFragment onPause()")
        super.onPause()
    }

    override fun onDestroy() {
        Log.d("hakjin", "SearchResultFragment onDestroy()")
        super.onDestroy()
    }

    private fun clickListenerMange(){
        resultAdapter.setOnSRClickListener(this) // 검색 결과 클릭리스너 구현
    }

    private fun manageTabLayout(){
        search_tab_layout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabReselected(tab: TabLayout.Tab?) { /* adapter 에 넣을 리스트를 초기화 0 -> 주변장소 1 -> 추천장소 */
                when (tab?.position){
                    0 -> initSearchResultList(0)
                    1 -> initSearchResultList(1)
                }
                changeResultData()
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position){
                    0 -> initSearchResultList(0)
                    1 -> initSearchResultList(1)
                }
                changeResultData()
            }
        })
    }

    private fun changeResultData() {
        rv_search_result.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            setHasFixedSize(true)
            rv_search_result.adapter = resultAdapter
        }
    }

    private fun initSearchResultList(op: Int){
        /*adapter 에 넣을 리스트를 초기화 0 -> 주변장소 1 -> 추천장소 */
        resultAdapter = SearchResultAdapter()
        when(op){
            0 -> for(i in 1..20){
                resultAdapter.addItem(SearchResultPlace(R.drawable.image03,"학진",100,
                        "유니온썬 타워","한식","100KM")
                )
            }
            1 -> for(i in 1..20){
                resultAdapter.addItem(
                    SearchResultPlace(R.drawable.image03,"종윤",200,
                        "프리빌리지","양식","200KM")
                )
            }
        }
    }
    override fun onBackPressed() {
        //mainAct.supportFragmentManager.popBackStack()
        cfm.popBackStack()
//        val i = cfm.backStackEntryCount
//        Log.d("hakjin", "SearchResultFragment onBackPressed$i")
//        when(cfm.backStackEntryCount){
//            0 -> {
//                mainAct.setOnBackPressedListener(this)
//                mainAct.onBackPressed()
//                Log.d("hakjin","stackcount = 0")
//            }
//            else -> {
//                cfm.popBackStack()
//            }
//        }
        Log.d("hakjin", "SearchResultFragment onBackPressed")
    }

    override fun onSRItemClicked(view: View, position: Int) {
        Log.d("hakjin", "검색결과 클릭$position")
        requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragment_container,
            mapFragment).commit()
    }
}
