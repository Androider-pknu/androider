package com.professionalandroid.apps.androider.newsfeed.commonsearch

import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.toSpannable
import androidx.recyclerview.widget.LinearLayoutManager
import com.professionalandroid.apps.androider.MainActivity

import com.professionalandroid.apps.androider.R
import com.professionalandroid.apps.androider.newsfeed.place.partranking.PartRank
import com.professionalandroid.apps.androider.newsfeed.place.search.SearchListFragment
import com.professionalandroid.apps.androider.newsfeed.searchlist.EachLocal
import kotlinx.android.synthetic.main.fragment_only_place_item_search_list.view.*

class OnlyPlaceItemSearchList(val mainContext:MainActivity) : Fragment() {

    var hintOfTest: String? = null
    lateinit var thisView:View
    lateinit var firstAdapter: ForFirstListAdapter
    lateinit var secondAdapter:ForSecondListAdapter
    var first= arrayListOf<String>()//모든 것이 들어 있는 리스트.
    var second=arrayListOf<String>()
    var subSecond= arrayListOf<ForSecondList>()
    var firstHintList= arrayListOf<ForFirstList>()//검색에 따라 걸러낸 결과가 들어있는 리스트.
    var secondHintList= arrayListOf<ForSecondList>()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view=inflater.inflate(R.layout.fragment_only_place_item_search_list, container, false)
        thisView=view
        if(first.size==0) makeList1()//계속 이 프래그먼트가 호출될때 마다 first 에 추가가 되므로 이러한 조건문 추가.
        if(second.size==0) makeList2()
        setFirstRecyclerView()
        setSecondRecyclerView()
        return view
    }
    fun setFirstRecyclerView(){
        if(hintOfTest!=null) {
            thisView.first_list.layoutManager = LinearLayoutManager(mainContext)
            thisView.first_list.setHasFixedSize(true)
            makeFirstHintList()
            firstAdapter = ForFirstListAdapter(firstHintList)
            thisView.first_list.adapter = firstAdapter
        }
    }
    fun setSecondRecyclerView(){
        if(secondHintList!=null){
            thisView.second_list.layoutManager=LinearLayoutManager(mainContext)
            thisView.second_list.setHasFixedSize(true)
            makeSecondHintList()
            secondAdapter=ForSecondListAdapter(secondHintList)
            thisView.second_list.adapter=secondAdapter
        }
    }
    private fun makeFirstHintList(){
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
            if(hintFlag) firstHintList.add(ForFirstList(changeTextColor(first[i],0,length)))
        }
    }
    private fun makeSecondHintList(){
        var length= hintOfTest!!.length
        var start:Int
        for(i in 0 until second.size){
            if(!second[i].contains(hintOfTest!!)) continue
            start=second[i].indexOf(hintOfTest!!)
            secondHintList.add(ForSecondList(changeTextColor(second[i],start,start+length),subSecond[i].mainDish,subSecond[i].address,subSecond[i].distance))
        }
    }
    private fun changeTextColor(text:String,start:Int,end:Int):SpannableString{
        var spannableString=SpannableString(text)
        spannableString.setSpan(ForegroundColorSpan(Color.parseColor("#006F20")),start,end,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        return spannableString
    }
    private fun makeList1(){
        first.add("광안리")
        first.add("광안점")
        first.add("광안")
        first.add("해운대")
        first.add("해운대점")
        first.add("운광점")
    }
    private fun makeList2(){
        second.add("해운대 다퍼주는 집&조개찜 경성대점")
        second.add("해운대 31cm 해물칼국수 광안점")
        second.add("해운대 삼계탕 2호점")
        second.add("닭발집 해운대점")
        second.add("광안리에 가자~")
        second.add("수변횟집 광안점")
        subSecond.add(ForSecondList(null,"조개","씨이발","8km"))
        subSecond.add(ForSecondList(null,"칼국수,만두","부산 해운대 구","7km"))
        subSecond.add(ForSecondList(null,"치킨","부산 해운대 구","5km"))
        subSecond.add(ForSecondList(null,"만두","부산 해운대 구","6km"))
        subSecond.add(ForSecondList(null,"라면","평양","10kmm"))
        subSecond.add(ForSecondList(null,"ㅈ같네","부산 해운대 구","4km"))
    }
}
