package com.professionalandroid.apps.androider

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_newsfeed.view.*
import kotlinx.android.synthetic.main.item_search.view.*
import kotlinx.android.synthetic.main.user_search.view.*

class UserSearchFrag(val newsFeedFragment: View) : Fragment(){
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view=inflater.inflate(R.layout.user_search,container,false)
        newsFeedFragment.viewPager.beginFakeDrag()
        view.user_search_bar.isIconified=false//SearchView 키보드 띄우기.
        view.user_search_bar.setIconifiedByDefault(false)//remove delete button in search view
        setCancelButton(view)
        return view
    }
    private fun setCancelButton(view: View){
        view.user_search_cancel.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
            requireActivity().navigation_main_bottom.visibility= View.VISIBLE
            hideKeyboard(view)
            newsFeedFragment.viewPager.endFakeDrag()
        }
    }
    private fun hideKeyboard(view: View){
        val imm= requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.user_search_bar.windowToken,0)//첫번째 인자는 기능상 키보드가 사라짐과 연관있는 view 를 넣어야함.
        //여기서는 Search View 가 연관있기에 넣음.
    }
}