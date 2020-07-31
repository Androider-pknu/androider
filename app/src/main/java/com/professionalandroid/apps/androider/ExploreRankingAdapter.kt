package com.professionalandroid.apps.androider

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView

class ExploreRankingAdapter(val list:ArrayList<ExploreRanking>) : RecyclerView.Adapter<ExploreRankingAdapter.CustomViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExploreRankingAdapter.CustomViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.ranking,parent,false)
        return CustomViewHolder(view)
    }
    interface OnRankingClickListener{
        fun onClick(view:View,position:Int)
    }
    private lateinit var rankingClickListener: OnRankingClickListener
    fun setRankingClickListener(rankingClickListener: OnRankingClickListener){
        this.rankingClickListener=rankingClickListener
    }
    override fun getItemCount()=list.size

    override fun onBindViewHolder(holder: ExploreRankingAdapter.CustomViewHolder, position: Int) {
        holder.image.setImageResource(list.get(position).image)
        holder.nickname1.text=list.get(position).nickname1
        holder.nickname2.text=list.get(position).nickname2
        holder.profile.text=list.get(position).profile
        holder.many.text=list[position].many
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
            rankingClickListener.onClick(it,position)
        }
    }
    class CustomViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val image=itemView.findViewById<ImageView>(R.id.profile_img)
        val nickname1=itemView.findViewById<TextView>(R.id.nickname1)
        val nickname2=itemView.findViewById<TextView>(R.id.nickname2)
        val profile=itemView.findViewById<TextView>(R.id.profile)
        val many=itemView.findViewById<TextView>(R.id.how_many)
        val follow_btn=itemView.findViewById<Button>(R.id.follow_btn)
        val rank_layout=itemView.findViewById<ConstraintLayout>(R.id.rank_layout)
    }
}