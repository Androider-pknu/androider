package com.professionalandroid.apps.androider

import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView

class KindOfFoodAdapter(val list:ArrayList<KindOfFood>) : RecyclerView.Adapter<KindOfFoodAdapter.CustomViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KindOfFoodAdapter.CustomViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.kind_of_food,parent,false)
        return CustomViewHolder(view)
    }

    override fun getItemCount()=list.size

    override fun onBindViewHolder(holder: KindOfFoodAdapter.CustomViewHolder, position: Int) {
        holder.cate_btn.text=list.get(position).item
    }
    class CustomViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val cate_btn=itemView.findViewById<Button>(R.id.category_btn)//버튼.
    }
}