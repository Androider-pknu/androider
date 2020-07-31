package com.professionalandroid.apps.androider

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView

class PartRankAdapter(val list:ArrayList<PartRank>) : RecyclerView.Adapter<PartRankAdapter.CustomViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PartRankAdapter.CustomViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_part_rank,parent,false)
        return CustomViewHolder(view)
    }
    interface OnPartRankClickListener{
        fun onClick(view:View,position: Int)
    }
    private lateinit var partRankClickListener: OnPartRankClickListener
    fun setPartLankClickListener(partRankClickListener: OnPartRankClickListener){
        this.partRankClickListener=partRankClickListener
    }
    override fun getItemCount()=list.size

    override fun onBindViewHolder(holder: PartRankAdapter.CustomViewHolder, position: Int) {
        holder.imageOfFood.setBackgroundResource(list.get(position).img)
        holder.nameOfFood.text=list.get(position).name
        holder.categoryOfFood.text=list.get(position).category
        holder.rank.text=list.get(position).rank
        holder.imageOfFood.setOnClickListener {
            partRankClickListener.onClick(it,position)
        }
    }
    class CustomViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val imageOfFood=itemView.findViewById<ConstraintLayout>(R.id.part_food_img)//사진.
        val nameOfFood=itemView.findViewById<TextView>(R.id.part_name)//이름.
        val categoryOfFood=itemView.findViewById<TextView>(R.id.part_category)//카테고라.
        val rank=itemView.findViewById<TextView>(R.id.part_rank_list)//순위.
    }
}