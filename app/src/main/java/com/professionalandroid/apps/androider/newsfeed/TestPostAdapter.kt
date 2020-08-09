package com.professionalandroid.apps.androider.newsfeed

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.professionalandroid.apps.androider.R
import com.professionalandroid.apps.androider.newsfeed.place.partranking.PartRank

class TestPostAdapter(val list: ArrayList<TestPost>) : RecyclerView.Adapter<TestPostAdapter.CustomViewHolder>(){
    //lateinit var list:ArrayList<TestPost>
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.place_all_posts,parent,false)
        return CustomViewHolder(
            view
        )
    }
    override fun getItemCount()=list.size

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.num.text=list[position].author.toString()
        holder.mainContent.text=list[position].content
        holder.time.text=list[position].timestamp
        holder.likeCount.text=list[position].likeCount.toString()
    }
    class CustomViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val num=itemView.findViewById<TextView>(R.id.author)
        val time=itemView.findViewById<TextView>(R.id.time)
        val mainContent=itemView.findViewById<TextView>(R.id.content)
        val heartImage=itemView.findViewById<ImageView>(R.id.like)
        val likeCount=itemView.findViewById<TextView>(R.id.like_count)
    }
}