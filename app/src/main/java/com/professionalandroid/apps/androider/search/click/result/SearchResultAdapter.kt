package com.professionalandroid.apps.androider.search.click.result

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.professionalandroid.apps.androider.R

class SearchResultAdapter(): RecyclerView.Adapter<SearchResultAdapter.SearchResultViewHolder>(){
    private var  list = ArrayList<SearchResultPlace>()

    interface OnSRItemClickListener{
        fun onSRItemClicked(view: View, position: Int)
    }

    private lateinit var mListener: OnSRItemClickListener

    fun setOnSRClickListener(listener: OnSRItemClickListener){
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_search_result_place,parent,false)
        return SearchResultViewHolder(
            view
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: SearchResultViewHolder, position: Int) {
        holder.placeName.text = list[position].name
        holder.placePostCount.text = list[position].postCount.toString()
        holder.placeLocation.text = list[position].location
        holder.placeCategory.text = list[position].category
        holder.placeDistance.text = list[position].distance

        holder.itemView.setOnClickListener {  // 클릭리스너 설정
            mListener.onSRItemClicked(it,position)
        }
    }

    fun addItem(item: SearchResultPlace){
        list.add(item)
    }

    class SearchResultViewHolder(searchResultItemView: View): RecyclerView.ViewHolder(searchResultItemView){
        val placeName = searchResultItemView.findViewById<TextView>(R.id.tv_searchresult_name)
        val placePostCount = searchResultItemView.findViewById<TextView>(R.id.tv_searchresult_postcount)
        val placeLocation = searchResultItemView.findViewById<TextView>(R.id.tv_searchresult_location)
        val placeCategory = searchResultItemView.findViewById<TextView>(R.id.tv_searchresult_category)
        val placeDistance = searchResultItemView.findViewById<TextView>(R.id.tv_searchresult_distance)
    }
}