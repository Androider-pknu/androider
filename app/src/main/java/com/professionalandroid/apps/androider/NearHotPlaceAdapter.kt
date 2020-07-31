package com.professionalandroid.apps.androider

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NearHotPlaceAdapter (private val nearHotPlaceList: ArrayList<NearHotPlace>): RecyclerView.Adapter<NearHotPlaceAdapter.NearHotPlaceViewHolder>(){

    interface OnNHPItemClickListener{
        fun onNHPItemClicked(view: View, position: Int)
    }

    private lateinit var mListener: OnNHPItemClickListener

    fun setOnNHPClickListener(listener: OnNHPItemClickListener){
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NearHotPlaceViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_near_hotplace,parent,false)
        return NearHotPlaceViewHolder(view)
    }

    override fun getItemCount(): Int {
        return nearHotPlaceList.size
    }

    override fun onBindViewHolder(holder: NearHotPlaceViewHolder, position: Int) {
        holder.name.text = nearHotPlaceList[position].name.toString()
        holder.location.text = nearHotPlaceList[position].location.toString()
        holder.img.setImageResource(nearHotPlaceList[position].img)

        holder.itemView.setOnClickListener {
            mListener.onNHPItemClicked(it,position)
        }
    }

    class NearHotPlaceViewHolder(nearHotPlaceItemView: View): RecyclerView.ViewHolder(nearHotPlaceItemView){
        val img = nearHotPlaceItemView.findViewById<ImageView>(R.id.img_hot_place)
        val name = nearHotPlaceItemView.findViewById<TextView>(R.id.tv_hot_place_name)
        val location = nearHotPlaceItemView.findViewById<TextView>(R.id.tv_hot_place_location)
    }
}

