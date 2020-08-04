package com.professionalandroid.apps.androider.newsfeed.commonsearch

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.professionalandroid.apps.androider.R
import kotlinx.android.synthetic.main.have_not_searched.view.*

class HaveNotSearched() : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.have_not_searched, container, false)
        setDefaultMessage(view)
        return view
    }
    private fun setDefaultMessage(view:View){
        val index=AllSearchView.index
        if(index==0) view.no_search_result.text="최근에 검색한 장소가 없습니다."
        else if(index==1) view.no_search_result.text="최근에 검색한 아이템이 없습니다."
        else if(index==2) view.no_search_result.text="최근에 검색한 사용자가 없습니다."
    }
}