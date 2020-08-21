package com.professionalandroid.apps.androider.search.click

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.professionalandroid.apps.androider.R
import com.professionalandroid.apps.androider.model.StoreDTO

class NearHotPlaceAdapter (private val con: Context, private val location: String): RecyclerView.Adapter<NearHotPlaceAdapter.NearHotPlaceViewHolder>(){
    private var list = ArrayList<StoreDTO>()

    interface OnNHPItemClickListener{
        fun onNHPItemClicked(view: View, position: Int)
    }

    private lateinit var mListener: OnNHPItemClickListener

    fun setOnNHPClickListener(listener: OnNHPItemClickListener){
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NearHotPlaceViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_near_hotplace,parent,false)
        return NearHotPlaceViewHolder(
            view
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setList(itemList: ArrayList<StoreDTO>){
        list = itemList
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: NearHotPlaceViewHolder, position: Int) {
        holder.name.text = " ${position+1}. ${list[position].name}"
        holder.location.text = location

        if(list[position].image_url==null){
            holder.img.setImageResource(R.drawable.koreanfood_basic)
        }
        else{
            Glide.with(con).load(list[position].image_url).into(holder.img)
        }

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

