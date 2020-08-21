package com.professionalandroid.apps.androider.search.click.doing

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.professionalandroid.apps.androider.R

// 검색어 입력중에 뜨는 근처 장소 어댑터
class SearchNearPlaceAdapter(private val nearPlaceList: ArrayList<SearchNearPlace>): RecyclerView.Adapter<SearchNearPlaceAdapter.NearPlaceViewHolder>(){

    interface OnSNHItemClickListener {
        fun onSNHItemClicked(view: View, position: Int)
    }

    private lateinit var mListener: OnSNHItemClickListener

    fun setOnSNHClickListener(listener: OnSNHItemClickListener) {
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NearPlaceViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_location_category,parent,false)
        return NearPlaceViewHolder(
            view
        )
    }
    override fun getItemCount(): Int {
        return nearPlaceList.size
    }

    override fun onBindViewHolder(holder: NearPlaceViewHolder, position: Int) {
        holder.nearPlaceName.text = nearPlaceList[position].nearPlaceName
        holder.nearPlaceLocation.text = nearPlaceList[position].nearPlaceLocation
        holder.nearPlaceCategory.text = nearPlaceList[position].nearPlaceCategory
        holder.nearPlaceDistance.text = nearPlaceList[position].nearPlaceDistance

        holder.itemView.setOnClickListener {
            mListener.onSNHItemClicked(it,position)
        }
    }
    //근처 장소 뷰 홀더
    class NearPlaceViewHolder(NearPlaceItem: View) : RecyclerView.ViewHolder(NearPlaceItem){
        val nearPlaceName = NearPlaceItem.findViewById<TextView>(R.id.tv_nearPlace_name)
        val nearPlaceLocation = NearPlaceItem.findViewById<TextView>(R.id.tv_nearPlace_location)
        val nearPlaceCategory = NearPlaceItem.findViewById<TextView>(R.id.tv_nearPlace_category)
        val nearPlaceDistance = NearPlaceItem.findViewById<TextView>(R.id.tv_nearPlace_distance)
    }
}

