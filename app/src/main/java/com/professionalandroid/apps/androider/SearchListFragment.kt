package com.professionalandroid.apps.androider

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_search_list.view.*

class SearchListFragment : Fragment() {
    companion object{
        var hintOfTest:String?=null
        lateinit var thisView: View
    }
    lateinit var adapter:EachLocalAdapter
    val cityList= arrayListOf<String>()
    val hintList= arrayListOf<EachLocal>()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view=inflater.inflate(R.layout.fragment_search_list, container, false)
        thisView=view
        makeList()
        setRecyclerView()
        return view
    }
    private fun makeList(){
        cityList.add("서울특별시")
        cityList.add("부산광역시")
        cityList.add("울산광역시")
        cityList.add("대전광역시")
        cityList.add("대구광역시")
        cityList.add("광주광역시")
        cityList.add("인천광역시")
        cityList.add("제주특별자치도")
        cityList.sort()
    }
    fun setRecyclerView(){
        if(hintOfTest!=null){
            thisView.search_list.layoutManager=LinearLayoutManager(requireContext())
            thisView.search_list.setHasFixedSize(true)
            makeHintList()
            adapter= EachLocalAdapter(hintList)
            adapter.setEachLocalClickListener(object:EachLocalAdapter.OnEachLocalClickListener{
                override fun onClick(view: View, position: Int, localName: TextView) {
                    PlaceFrag.local_btn.text=localName.text as String
                    PlaceSearch.quitBtn.performClick()
                }
            })
            thisView.search_list.adapter=adapter
        }
    }
    private fun makeHintList(){
        var length= hintOfTest!!.length
        var hintFlag:Boolean
        for(i  in 0 until cityList.size){
            hintFlag=true
            var hint:String=cityList[i]
            for(j in 0 until length){
                if(hint[j]!=hintOfTest!![j]){
                    hintFlag=false
                    break
                }
            }
            if(hintFlag) hintList.add(EachLocal(R.drawable.local_location,cityList[i],"1111km"))
        }
    }
}
