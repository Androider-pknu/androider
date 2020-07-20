package com.professionalandroid.apps.androider

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.professionalandroid.apps.androider.R

class ItemImageButtonAdapter(val list:ArrayList<ItemImageButton>) : RecyclerView.Adapter<ItemImageButtonAdapter.CustomViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemImageButtonAdapter.CustomViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.item_food,parent,false)
        return CustomViewHolder(view)
    }

    override fun getItemCount()=list.size

    override fun onBindViewHolder(holder: ItemImageButtonAdapter.CustomViewHolder, position: Int) {
        holder.imageOfFood.setImageResource(list.get(position).foodImage)
        holder.nameOfFood.text=list.get(position).foodName
        holder.categoryOfFood.text=list.get(position).category
    }
    class CustomViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val imageOfFood=itemView.findViewById<ImageButton>(R.id.food_image)//사진.
        val nameOfFood=itemView.findViewById<TextView>(R.id.food_name)//이름.
        val categoryOfFood=itemView.findViewById<TextView>(R.id.food_category)//카테고라.
    }
}