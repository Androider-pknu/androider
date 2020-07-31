package com.professionalandroid.apps.androider.newsfeed.searchlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.professionalandroid.apps.androider.R

class EachLocalAdapter(val list:ArrayList<EachLocal>) : RecyclerView.Adapter<EachLocalAdapter.CustomViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.each_local,parent,false)
        return CustomViewHolder(
            view
        )
    }
    interface OnEachLocalClickListener{
        fun onClick(view: View, position: Int, localName:TextView)
    }
    private lateinit var eachLocalClickListener: OnEachLocalClickListener
    fun setEachLocalClickListener(eachLocalClickListener: OnEachLocalClickListener){
        this.eachLocalClickListener=eachLocalClickListener
    }
    override fun getItemCount()=list.size

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.img.setImageResource(list[position].img)
        holder.localName.text=list[position].localName
        holder.localDistance.text=list[position].distance
        holder.localLayout.setOnClickListener{
            eachLocalClickListener.onClick(it,position,holder.localName)
        }
    }
    class CustomViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val img=itemView.findViewById<ImageView>(R.id.imageView)
        val localName=itemView.findViewById<TextView>(R.id.local_name)
        val localDistance=itemView.findViewById<TextView>(R.id.local_distance)
        val localLayout=itemView.findViewById<ConstraintLayout>(R.id.local_layout)
    }
}