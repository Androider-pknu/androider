package com.professionalandroid.apps.androider

import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RankingAdapter(val list:ArrayList<Ranking>) : RecyclerView.Adapter<RankingAdapter.CustomViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RankingAdapter.CustomViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.ranking,parent,false)
        return CustomViewHolder(view)
    }

    override fun getItemCount()=list.size

    override fun onBindViewHolder(holder: RankingAdapter.CustomViewHolder, position: Int) {
        holder.image.setImageResource(list.get(position).image)
        holder.nickname1.text=list.get(position).nickname1
        holder.nickname2.text=list.get(position).nickname2
        holder.profile.text=list.get(position).profile
    }
    class CustomViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val image=itemView.findViewById<ImageView>(R.id.image)
        val nickname1=itemView.findViewById<TextView>(R.id.nickname1)
        val nickname2=itemView.findViewById<TextView>(R.id.nickname2)
        val profile=itemView.findViewById<TextView>(R.id.profile)
    }
}