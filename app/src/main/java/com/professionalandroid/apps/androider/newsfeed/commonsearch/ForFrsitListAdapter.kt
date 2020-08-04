package com.professionalandroid.apps.androider.newsfeed.commonsearch

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.professionalandroid.apps.androider.R

class ForFirstListAdapter(val list:ArrayList<ForFirstList>) : RecyclerView.Adapter<ForFirstListAdapter.CustomViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.recycler_for_first_list,parent,false)
        return CustomViewHolder(
            view
        )
    }
    override fun getItemCount()=list.size

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.text.text=list[position].text
    }
    class CustomViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val text=itemView.findViewById<TextView>(R.id.first_text)
    }
}