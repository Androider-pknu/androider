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
    lateinit var searchView:View
    lateinit var mSuper: MainActivity
    var searchList:OnlyPlaceItemSearchList= OnlyPlaceItemSearchList(mainContext)
    var queryFlag:Boolean=false
    lateinit var haveNotSearched:HaveNotSearched
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?{
        val view=inflater.inflate(R.layout.fragment_search_view,container,false)
        searchView =view
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
        val imm= requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.search_bar.windowToken,0)//첫번째 인자는 기능상 키보드가 사라짐과 연관있는 view 를 넣어야함.
        //여기서는 Search View 가 연관있기에 넣음.
    }
    fun setHint(view:View){
        queryFlag=false
        if(index ==0) {
            view.search_bar.queryHint="장소명을 입력하세요"
            //view.no_search_result.text="최근에 검색한 장소가 없습니다."
            haveNotSearched()
            setOnQueryTextChange(view)
        }
        else if(index ==1) {
            view.search_bar.queryHint="아이템명 or 메이커를 입력하세요"
            //view.no_search_result.text="최근에 검색한 아이템이 없습니다."
            haveNotSearched()
        }
        else if(index ==2) {
            view.search_bar.queryHint="사용자명을 입력하세요"
            //view.no_search_result.text="최근에 검색한 사용자가 없습니다."
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
//                local_search.clearFocus()
//                if(p0!=null) SearchListFragment.hintOfTest =p0
//                supportFragmentManager.beginTransaction().replace(R.id.local_search_frame, searchListFragment).commit()
//                searchListFragment.hintList.clear()
//                searchListFragment.setRecyclerView()
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                if(p0!=null) searchList.hintOfTest =p0
                requireActivity().supportFragmentManager.beginTransaction().replace(R.id.all_search_list,searchList).commit()
                if(queryFlag){
                    searchList.hintList.clear()
                    searchList.setFirstRecyclerView()
                }
                if(view.search_bar.query.isEmpty()) {
                    searchList.hintList.clear()
                    haveNotSearched()
//                    requireActivity().supportFragmentManager.beginTransaction().replace(R.id.all_search_list,haveNotSearched).commit()
                    //parentFragmentManager.beginTransaction().replace(R.id.all_search_list,haveNotSearched).commit()
                }
                queryFlag=true
                return true
            }
        })
    }
    override fun onBackPressed(){
        mSuper.supportFragmentManager.popBackStack()
        mSuper.navigation_main_bottom.visibility = View.VISIBLE
        hideKeyboard(searchView)
        newsFeedFragment.search_button.visibility = View.VISIBLE //newsFeedFragment.viewPager.endFakeDrag()
        newsFeedFragment.viewPager.isUserInputEnabled = true//enable to swipe viewpager2 when search view off
        NewsFeedFragment.flag = true
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mSuper= context as MainActivity
        mSuper.setOnBackPressedListener(this)
    }
}