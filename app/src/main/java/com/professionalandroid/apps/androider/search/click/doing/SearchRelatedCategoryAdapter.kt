package com.professionalandroid.apps.androider.search.click.doing

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.professionalandroid.apps.androider.R

// 연관 카테고리 어댑터 //
class SearchRelatedCategoryAdapter (private val relatedCategoryList: ArrayList<SearchRelatedCategory>) :
    RecyclerView.Adapter<SearchRelatedCategoryAdapter.RelatedCategoryViewHolder>(){

    interface OnSRCItemClickListener{
        fun onSRCItemClicked(view: View, position: Int)
    }

    private lateinit var mListener: OnSRCItemClickListener

    fun setOnSRClickListener(listener: OnSRCItemClickListener){
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RelatedCategoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_search_related_category,parent,false)
        return RelatedCategoryViewHolder(
            view
        )
    }

    override fun getItemCount(): Int {
        return relatedCategoryList.size
    }

    override fun onBindViewHolder(holder: RelatedCategoryViewHolder, position: Int) {
        holder.categoryLocationName.text = relatedCategoryList[position].relatedName

        holder.itemView.setOnClickListener {
            mListener.onSRCItemClicked(it,position)
        }
    }

    class RelatedCategoryViewHolder(categoryLocationView: View): RecyclerView.ViewHolder(categoryLocationView){
        val categoryLocationName = categoryLocationView.findViewById<TextView>(R.id.tv_related_category) // 카테고리
    }
}
