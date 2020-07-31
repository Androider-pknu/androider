package com.professionalandroid.apps.androider.newsfeed.user.more.social

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.professionalandroid.apps.androider.R

class SocialRankingAdapter(val list:ArrayList<SocialRanking>) : RecyclerView.Adapter<SocialRankingAdapter.CustomViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.social_ranking,parent,false)
        return CustomViewHolder(view)
    }
    interface OnSocialRankingClickListener{
        fun onClick(view: View, position:Int)
    }
    private lateinit var socialRankingClickListener: OnSocialRankingClickListener
    fun setSocialRankingClickListener(rankingClickListener: OnSocialRankingClickListener){
        this.socialRankingClickListener=rankingClickListener
    }
    override fun getItemCount()=list.size

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.image.setImageResource(list.get(position).image)
        holder.nickname1.text=list.get(position).nickname1
        holder.nickname2.text=list.get(position).nickname2
        holder.profile.text=list.get(position).profile
        var flag=true
        holder.follow_btn.setOnClickListener {
            if(flag){
                holder.follow_btn.setBackgroundResource(R.drawable.follewer_btn)
                holder.follow_btn.text="팔로잉"
                holder.follow_btn.setTextColor(Color.parseColor("#000000"))
                flag=false
            }
            else{
                holder.follow_btn.setBackgroundResource(R.drawable.follow_btn)
                holder.follow_btn.text="팔로우"
                holder.follow_btn.setTextColor(Color.parseColor("#FFFFFF"))
                flag=true
            }
        }
        holder.rank_layout.setOnClickListener {
            socialRankingClickListener.onClick(it,position)
        }
    }
    class CustomViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val image=itemView.findViewById<ImageView>(R.id.social_profile_img)
        val nickname1=itemView.findViewById<TextView>(R.id.social_nickname1)
        val nickname2=itemView.findViewById<TextView>(R.id.social_nickname2)
        val profile=itemView.findViewById<TextView>(R.id.social_profile)
        val follow_btn=itemView.findViewById<Button>(R.id.social_follow_btn)
        val rank_layout=itemView.findViewById<ConstraintLayout>(R.id.social_rank_layout)
    }
}