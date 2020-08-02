package com.professionalandroid.apps.androider.newsfeed.commonsearch

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager

import com.professionalandroid.apps.androider.R
import com.professionalandroid.apps.androider.newsfeed.place.partranking.PartRank
import com.professionalandroid.apps.androider.newsfeed.place.search.SearchListFragment
import com.professionalandroid.apps.androider.newsfeed.searchlist.EachLocal
import kotlinx.android.synthetic.main.fragment_only_place_item_search_list.view.*

class OnlyPlaceItemSearchList : Fragment() {

    var hintOfTest: String? = null
    lateinit var thisView:View
    lateinit var adapter: ForFirstListAdapter
    var first= arrayListOf<String>()//모든 것이 들어 있는 리스트.
    var hintList= arrayListOf<ForFirstList>()//검색에 따라 걸러낸 결과가 들어있는 리스트.
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view=inflater.inflate(R.layout.fragment_only_place_item_search_list, container, false)
        thisView=view
        makeList()
        setFirstRecyclerView()
        return view
    }
    fun setFirstRecyclerView(){
        if(hintOfTest!=null) {
            thisView.first_list.layoutManager = LinearLayoutManager(requireContext())
            thisView.first_list.setHasFixedSize(true)
            makeHintList()
            adapter = ForFirstListAdapter(hintList)
            thisView.first_list.adapter = adapter
        }
    }
    private fun makeHintList(){
        var length= hintOfTest!!.length
        var hintFlag:Boolean
        for(i  in 0 until first.size){
            hintFlag=true
            var hint:String=first[i]
            if(hint.length<length) continue//예를 들어서 입력된게 광안리이고 비교할 대상이 광안이라면 광안은 index 예외가 발생하기에.
            for(j in 0 until length){
                if(hint[j]!= hintOfTest!![j]){
                    hintFlag=false
                    break
                }
            }
            if(hintFlag) hintList.add(ForFirstList(first[i]))
        }
    }
    private fun makeList(){
        first.add("광안리")
        first.add("광안점")
        first.add("광안")
        first.add("해운대")
        first.add("해운대점")
        first.add("운광점")
    }
}
