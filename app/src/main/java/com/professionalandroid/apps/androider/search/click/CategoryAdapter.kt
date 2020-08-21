package com.professionalandroid.apps.androider.search.click

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.professionalandroid.apps.androider.R

class CategoryAdapter(private val categoryList: ArrayList<Category>) : RecyclerView.Adapter<CategoryAdapter.CustomViewHolder>(){

    interface OnCategoryItemClickListener{
        fun onCategoryItemClicked(view: View, position: Int)
    }

    lateinit var mListener: OnCategoryItemClickListener

    fun setOnClickListener(listener: OnCategoryItemClickListener){
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_search_category, parent, false)
        return CustomViewHolder(
            view
        )
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.categoryName.text = categoryList[position].categoryName

        holder.categoryName.setOnClickListener {
            mListener.onCategoryItemClicked(it,position)
        }
    }
    class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val categoryName = itemView.findViewById<TextView>(R.id.btn_category) // 카테고리

    }
}












