package com.professionalandroid.apps.androider

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SearchNearPlaceAdapter(val nearPlaceList: ArrayList<NearPlace>): RecyclerView.Adapter<SearchNearPlaceAdapter.NearPlaceViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchNearPlaceAdapter.NearPlaceViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_location_category,parent,false)
        return NearPlaceViewHolder(view)
    }

    override fun getItemCount(): Int {
        return nearPlaceList.size
    }


    //nearPlaceName,Location,Category,PlaceDistance는 item_location_category 의 id값
    override fun onBindViewHolder(holder: SearchNearPlaceAdapter.NearPlaceViewHolder, position: Int) {
        holder.nearPlaceName.text = nearPlaceList[position].nearPlaceName
        holder.nearPlaceLocation.text = nearPlaceList[position].nearPlaceLocation
        holder.nearPlaceCategory.text = nearPlaceList[position].nearPlaceCategory
        holder.nearPlaceDistance.text = nearPlaceList[position].nearPlaceDistance

    }

    //근처 장소 뷰 홀더
    class NearPlaceViewHolder(NearPlaceItem: View) : RecyclerView.ViewHolder(NearPlaceItem){
        val nearPlaceName = NearPlaceItem.findViewById<TextView>(R.id.tv_nearPlace_name)
        val nearPlaceLocation = NearPlaceItem.findViewById<TextView>(R.id.tv_nearPlace_location)
        val nearPlaceCategory = NearPlaceItem.findViewById<TextView>(R.id.tv_nearPlace_category)
        val nearPlaceDistance = NearPlaceItem.findViewById<TextView>(R.id.tv_nearPlace_distance)
    }
}

