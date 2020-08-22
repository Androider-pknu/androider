package com.professionalandroid.apps.androider.newsfeed.user.more.social

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
import kotlinx.android.synthetic.main.fragment_social_more.view.*


class SocialMoreFragment : Fragment() {
    val adapter= SocialRankingAdapter(makeList())
    val thisFragment=this
    val profileFragment=
        ProfileFragment()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view= inflater.inflate(R.layout.fragment_social_more, container, false)
        setButton(view)
        setRecyclerView(view)
        setFollowButton(view.social_follow_btn)
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
        view.social_back.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }
    private fun setRecyclerView(view:View){
        view.social_rank_list.layoutManager= LinearLayoutManager(requireContext())
        view.social_rank_list.setHasFixedSize(true)
        adapter.setSocialRankingClickListener(object:
            SocialRankingAdapter.OnSocialRankingClickListener {
            override fun onClick(view:View,position:Int){
                moveToFragment(profileFragment)
            }
        })
        view.social_rank_list.adapter=adapter
    }
    private fun makeList():ArrayList<SocialRanking>{
        val list= arrayListOf<SocialRanking>()
        for(i in 2..20) list.add(SocialRanking(R.drawable.bread,"바닷가","@재밌게 살자","영웅호색."))
        return list
    }
    private fun setFollowButton(btn: Button){
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
        view.social_first_man.setOnClickListener {
            moveToFragment(profileFragment)
        }
    }
}
