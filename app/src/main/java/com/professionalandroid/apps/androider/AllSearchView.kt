package com.professionalandroid.apps.androider

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.professionalandroid.apps.androider.navigation.NewsFeedFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_newsfeed.*
import kotlinx.android.synthetic.main.fragment_newsfeed.view.*
import kotlinx.android.synthetic.main.fragment_search_view.view.*
import kotlinx.android.synthetic.main.item_search.view.*
import kotlinx.android.synthetic.main.place_search.view.*

class AllSearchView(private val newsFeedFragment: View) : Fragment(),OnBackPressedListener{
    companion object{
        var index:Int=-1
        lateinit var searchView:View
    }
    lateinit var mSuper:MainActivity
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?{
        val view=inflater.inflate(R.layout.fragment_search_view,container,false)
        searchView=view
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
                if(NewsFeedFragment.pageSet.size!=0) {
                    for (i in 0 until NewsFeedFragment.pageSet.size)
                        requireActivity().supportFragmentManager.beginTransaction().show(NewsFeedFragment.pageSet[i])
                }
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
        if(index==0) view.search_bar.queryHint="장소명을 입력하세요"
        else if(index==1) view.search_bar.queryHint="아이템명 or 메이커를 입력하세요"
        else if(index==2) view.search_bar.queryHint="사용자명을 입력하세요"
    }
    override fun onBackPressed(){
        Log.d("test","22222")
        requireActivity().supportFragmentManager.popBackStack()
        requireActivity().navigation_main_bottom.visibility = View.VISIBLE
        hideKeyboard(AllSearchView.searchView)
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