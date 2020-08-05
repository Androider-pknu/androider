package com.professionalandroid.apps.androider.newsfeed.commonsearch

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import androidx.fragment.app.Fragment
import com.professionalandroid.apps.androider.MainActivity
import com.professionalandroid.apps.androider.OnBackPressedListener
import com.professionalandroid.apps.androider.R
import com.professionalandroid.apps.androider.navigation.NewsFeedFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_newsfeed.view.*
import kotlinx.android.synthetic.main.fragment_search_view.view.*

class AllSearchView(private val newsFeedFragment: View,val mainContext: MainActivity) : Fragment(),OnBackPressedListener{
    companion object{
        var index:Int=-1
    }
    //lateinit var searchView:View
    var mSuper: MainActivity=mainContext
    var searchList:OnlyPlaceItemSearchList= OnlyPlaceItemSearchList(mainContext)
    var queryFlag:Boolean=false
    lateinit var haveNotSearched:HaveNotSearched
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?{
        val view=inflater.inflate(R.layout.fragment_search_view,container,false)
        //searchView =view
        //newsFeedFragment.viewPager.beginFakeDrag()
        newsFeedFragment.viewPager.isUserInputEnabled=false//disable to swipe viewpager2 when search view on.
        view.search_bar.isIconified=false//SearchView 키보드 띄우기.
        view.search_bar.setIconifiedByDefault(false)//remove delete button in search view
        setHint(view)
        setCancelButton(view)
        return view
    }
    private fun setCancelButton(view:View){
        view.search_cancel.setOnClickListener {
            if(!NewsFeedFragment.flag) {
                view.search_bar.setQuery(null,false)//남은 query 를 전부 삭제.
                requireActivity().supportFragmentManager.popBackStack()
                requireActivity().navigation_main_bottom.visibility = View.VISIBLE
                hideKeyboard(view)
                newsFeedFragment.search_button.visibility = View.VISIBLE //newsFeedFragment.viewPager.endFakeDrag()
                newsFeedFragment.viewPager.isUserInputEnabled = true//enable to swipe viewpager2 when search view off
                NewsFeedFragment.flag = true
            }
        }
    }
    private fun hideKeyboard(view:View){
        val imm= mainContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.search_bar.windowToken,0)//첫번째 인자는 기능상 키보드가 사라짐과 연관있는 view 를 넣어야함.
        //여기서는 Search View 가 연관있기에 넣음.
    }
    fun setHint(view:View){
        queryFlag=false
        if(index ==0) {
            view.search_bar.queryHint="장소명을 입력하세요"
            haveNotSearched()
            setOnQueryTextChange(view)
        }
        else if(index ==1) {
            view.search_bar.queryHint="아이템명 or 메이커를 입력하세요"
            haveNotSearched()
        }
        else if(index ==2) {
            view.search_bar.queryHint="사용자명을 입력하세요"
            haveNotSearched()
        }
    }
    private fun haveNotSearched(){
        haveNotSearched= HaveNotSearched()
        requireActivity().supportFragmentManager.beginTransaction().replace(R.id.all_search_list,haveNotSearched).commit()
    }
    private fun setOnQueryTextChange(view:View){
        view.search_bar.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {//검색버튼을 눌렀을 때.
                if(p0!=null) searchList.hintOfTest=p0
                //childFragmentManager.beginTransaction().replace(R.id.all_search_list,PlaceSearchResult()).commit()
                //parentFragmentManager.beginTransaction().replace(R.id.all_search_list,PlaceSearchResult()).commit()
                hideKeyboard(view)
                mSuper.navigation_main_bottom.visibility = View.VISIBLE
                requireActivity().supportFragmentManager.beginTransaction().replace(R.id.all_search_list,PlaceSearchResult(mainContext)).commit()
                return true
            }
            override fun onQueryTextChange(p0: String?): Boolean {
                if(p0!=null) searchList.hintOfTest =p0
                requireActivity().supportFragmentManager.beginTransaction().replace(R.id.all_search_list,searchList).commit()
                if(queryFlag){
                    searchList.firstHintList.clear()
                    searchList.setFirstRecyclerView()
                    searchList.secondHintList.clear()
                    searchList.setSecondRecyclerView()
                }
                if(view.search_bar.query.isEmpty()) {
                    searchList.firstHintList.clear()
                    searchList.secondHintList.clear()
                    haveNotSearched()
                }
                queryFlag=true
                return true
            }
        })
    }
    /*
    onAttach 에서 setOnBackPressedListener 를 호출하는 이유는 onAttach 가 Fragment 가 생성될 시 가장 먼저 호출되는 함수인데, 데이터가 로딩 도중 사용자가 back 키를 누를 수도
    있기 때문. 쉽게 말해서 onBackPressed 에서 setOnBackPressedListener 를 설정하면 onAttach 단계에서 back 키를 누를시 오류가 발생 할 수 있기 떄문.
    onDestroy 를 override 한 이유는 onAttach 에서 setOnBackPressedListener 를 호출하여 MainActivity 에 있는 mListener 를 초기화하였는데 onDestroy 에서 mListener 를
    null 로 설정하지 않는다면 다른 프래그먼트나 MainActivity 에서 back 키를 누르면 이 프래그먼트에 있는 onBackPressed 가 호출 될 수 잇기 때문.
     */
    override fun onBackPressed(){
        requireView().search_bar.setQuery(null,false)
        mSuper.supportFragmentManager.popBackStack()
        mSuper.navigation_main_bottom.visibility = View.VISIBLE
        hideKeyboard(requireView())
        newsFeedFragment.search_button.visibility = View.VISIBLE //newsFeedFragment.viewPager.endFakeDrag()
        newsFeedFragment.viewPager.isUserInputEnabled = true//enable to swipe viewpager2 when search view off
        NewsFeedFragment.flag = true
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mSuper= context as MainActivity
        mSuper.setOnBackPressedListener(this)
    }
    override fun onDestroy() {
        mSuper.setOnBackPressedListener(null)
        super.onDestroy()
    }
}