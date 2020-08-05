package com.professionalandroid.apps.androider.newsfeed.commonsearch

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.professionalandroid.apps.androider.R

class ForSecondListAdapter(val list:ArrayList<ForSecondList>) : RecyclerView.Adapter<ForSecondListAdapter.CustomViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.recycler_for_second_list,parent,false)
        return CustomViewHolder(view)
    }
    interface OnSecondListClickListener{
        fun onClick(view:View,position: Int)
    }
    private lateinit var secondListClickListener: OnSecondListClickListener
    fun setSecondListClickListener(secondListClickListener: OnSecondListClickListener){
        this.secondListClickListener=secondListClickListener
    }
    override fun getItemCount()=list.size

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.storeName.text=list[position].storeName
        holder.address.text=list[position].address
        holder.mainDish.text=list[position].mainDish
        holder.distance.text=list[position].distance
        holder.layout.setOnClickListener {
            secondListClickListener.onClick(it,position)
        }
    }
    class CustomViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val storeName=itemView.findViewById<TextView>(R.id.sec_list_store_name)
        val address=itemView.findViewById<TextView>(R.id.sec_list_address)
        val mainDish=itemView.findViewById<TextView>(R.id.sec_list_maindish)
        val distance=itemView.findViewById<TextView>(R.id.sec_list_distance)
        val layout=itemView.findViewById<ConstraintLayout>(R.id.for_second_list)
    }
}