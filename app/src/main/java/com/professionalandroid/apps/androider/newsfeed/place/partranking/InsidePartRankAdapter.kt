package com.professionalandroid.apps.androider.newsfeed.place.partranking

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.professionalandroid.apps.androider.R

class InsidePartRankAdapter(val list:ArrayList<InsidePartRank>) : RecyclerView.Adapter<InsidePartRankAdapter.CustomViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.inside_part_rank,parent,false)
        return CustomViewHolder(
            view
        )
    }
    interface OnInsidePartRankClickListener{
        fun onClick(view:View,position: Int)
    }
    private lateinit var insidePartRankClickListener: OnInsidePartRankClickListener
    fun setInsidePartRankClickListener(insidePartRankClickListener: OnInsidePartRankClickListener){
        this.insidePartRankClickListener=insidePartRankClickListener
    }
    override fun getItemCount()=list.size

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.imgOfRank.setImageResource(list[position].img)
        holder.nameOfRank.text=list[position].name
        holder.addressOfRank.text=list[position].address
        holder.imgOfRank.setOnClickListener{
            insidePartRankClickListener.onClick(it,position)
        }
    }
    class CustomViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val imgOfRank=itemView.findViewById<ImageView>(R.id.inside_rank_img)
        val nameOfRank=itemView.findViewById<TextView>(R.id.inside_rank_name)
        val addressOfRank=itemView.findViewById<TextView>(R.id.inside_rank_address)
    }
}