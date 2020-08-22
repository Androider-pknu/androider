package com.professionalandroid.apps.androider.newsfeed.user.more.explore

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import com.professionalandroid.apps.androider.R
import com.professionalandroid.apps.androider.newsfeed.ProfileFragment
import kotlinx.android.synthetic.main.fragment_ep_more.view.*

class ExploreMoreFragment() : Fragment() {
    val thisFragment=this
    val adapter=
        ExploreRankingAdapter(
            makeList()
        )
    private val profileFragment=
        ProfileFragment()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view=inflater.inflate(R.layout.fragment_ep_more, container, false)
        setButton(view)
        setRecyclerView(view)
        setFollowButton(view.follow_btn)
        setLayoutClick(view)
        return view
    }
    private fun moveToFragment(newFragment:Fragment){
        requireActivity().supportFragmentManager.beginTransaction().setCustomAnimations(
            R.anim.start_more_view,
            R.anim.left_view,
            R.anim.right_view,
            R.anim.end_more_view
        )
            .addToBackStack(null).add(R.id.layout_main_content,newFragment).hide(thisFragment).commit()
    }
    private fun setButton(view:View){
        view.back.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }
    private fun setRecyclerView(view:View){
        view.rank_list.layoutManager=LinearLayoutManager(requireContext())
        view.rank_list.setHasFixedSize(true)
        adapter.setRankingClickListener(object:
            ExploreRankingAdapter.OnRankingClickListener {
            override fun onClick(view:View,position:Int){
                moveToFragment(profileFragment)
            }
        })
        view.rank_list.adapter=adapter
    }
    private fun makeList():ArrayList<ExploreRanking>{
        val list= arrayListOf<ExploreRanking>()
        for(i in 2..20) list.add(
            ExploreRanking(
                R.drawable.bread,
                "이학진",
                "@전쟁이다",
                "저는 이학진입니다.",
                "100"
            )
        )
        return list
    }
    private fun setFollowButton(btn:Button){
        var flag=true
        btn.setOnClickListener {
            if(flag){
                btn.setBackgroundResource(R.drawable.follewer_btn)
                btn.text="팔로잉"
                btn.setTextColor(Color.parseColor("#000000"))
                flag=false
            }
            else{
                btn.setBackgroundResource(R.drawable.follow_btn)
                btn.text="팔로우"
                btn.setTextColor(Color.parseColor("#FFFFFF"))
                flag=true
            }
        }
    }
    private fun setLayoutClick(view:View){
        view.first_man.setOnClickListener {
            moveToFragment(profileFragment)
        }
    }
}
