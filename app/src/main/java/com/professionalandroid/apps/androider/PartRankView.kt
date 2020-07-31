package com.professionalandroid.apps.androider

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.HorizontalScrollView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.professionalandroid.apps.androider.navigation.NewsFeedFragment
import kotlinx.android.synthetic.main.dialog_searchaddress_countrypicker.*
import kotlinx.android.synthetic.main.fragment_part_rank_view.view.*

class PartRankView(val storePage: StorePage) : Fragment() {
    val thisFragment=this
    var insideAdapter=InsidePartRankAdapter(makeList())
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view= inflater.inflate(R.layout.fragment_part_rank_view, container, false)
        setRecyclerView(view)
        setBackButton(view)
        return view
    }
    private fun setRecyclerView(view:View){
        view.sub_part_rank_list.layoutManager=GridLayoutManager(requireContext(),2)
        view.sub_part_rank_list.setHasFixedSize(true)
        insideAdapter.setInsidePartRankClickListener(object: InsidePartRankAdapter.OnInsidePartRankClickListener{
            override fun onClick(view:View,position:Int){
                requireActivity().supportFragmentManager.beginTransaction()
                    .setCustomAnimations(R.anim.start_more_view,R.anim.left_view,R.anim.right_view,R.anim.end_more_view)
                    .addToBackStack(null).add(R.id.layout_main_content,storePage).hide(thisFragment).commit()
            }
        })
        view.sub_part_rank_list.adapter=insideAdapter
    }
    private fun makeList():ArrayList<InsidePartRank>{
        val list= arrayListOf<InsidePartRank>()
        for(i in 1..30) list.add(InsidePartRank(R.drawable.bread,i.toString()+"빵집","나도 몰라"))
        return list
    }
    private fun setBackButton(view:View){
        view.back.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }
}
