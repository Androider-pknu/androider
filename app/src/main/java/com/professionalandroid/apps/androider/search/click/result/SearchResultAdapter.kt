package com.professionalandroid.apps.androider.search.click.result

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.professionalandroid.apps.androider.R
import com.professionalandroid.apps.androider.model.StoreDTO
import kotlin.math.round
import kotlin.math.roundToInt

class SearchResultAdapter: RecyclerView.Adapter<SearchResultAdapter.SearchResultViewHolder>(){
    private var list = ArrayList<StoreDTO>()

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
        holder.placePostCount.text = "100 개" // 수정예정
        holder.placeLocation.text = list[position].address
        holder.placeCategory.text = list[position].category
        holder.placeDistance.text = decideUnit(round(list[position].distance*1000.0))  // KM -> Meter

        holder.itemView.setOnClickListener {  // 클릭리스너 설정
            mListener.onSRItemClicked(it,position)
        }
    }

    fun setList(itemList: ArrayList<StoreDTO>){
        list = itemList
        notifyDataSetChanged()
    }

    private fun decideUnit(dis: Double): String{ // 단위 결정
        return if(dis < 1000.0)
            dis.toString()+"m"
        else
            (((dis / 1000.0) * 10).roundToInt() /10.0).toString()+"KM"
    }

    class SearchResultViewHolder(searchResultItemView: View): RecyclerView.ViewHolder(searchResultItemView){
        val placeName = searchResultItemView.findViewById<TextView>(R.id.tv_searchresult_name)
        val placePostCount = searchResultItemView.findViewById<TextView>(R.id.tv_searchresult_postcount)
        val placeLocation = searchResultItemView.findViewById<TextView>(R.id.tv_searchresult_location)
        val placeCategory = searchResultItemView.findViewById<TextView>(R.id.tv_searchresult_category)
        val placeDistance = searchResultItemView.findViewById<TextView>(R.id.tv_searchresult_distance)
    }
}