package com.professionalandroid.apps.androider

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


// 연관 카테고리 어댑터 //
class SearchRelatedCategoryAdapter (val relatedCategoryList: ArrayList<SearchRelatedCategory>) :
    RecyclerView.Adapter<SearchRelatedCategoryAdapter.RelatedCategoryViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RelatedCategoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_search_related_category,parent,false)
        return RelatedCategoryViewHolder(view) // 커스텀뷰 홀더에 만든 view를 전달
    }

    override fun getItemCount(): Int {
        return relatedCategoryList.size
    }

    override fun onBindViewHolder(holder: RelatedCategoryViewHolder, position: Int) {
        holder.categoryLocationName.text = relatedCategoryList[position].relatedName
    }

    class RelatedCategoryViewHolder(categoryLocationView: View): RecyclerView.ViewHolder(categoryLocationView){
        val categoryLocationName = categoryLocationView.findViewById<TextView>(R.id.tv_related_category) // 카테고리
    }
}
