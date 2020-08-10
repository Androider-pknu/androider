package com.professionalandroid.apps.androider.newsfeed

import android.text.Layout
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.professionalandroid.apps.androider.R
import com.professionalandroid.apps.androider.newsfeed.place.partranking.PartRank

//class TestPostAdapter(var list:ArrayList<TestPost>): RecyclerView.Adapter<RecyclerView.ViewHolder>(){
//    companion object{
//        private const val TYPE_DATA=0
//        private const val TYPE_PROGRESS=1
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
//        return when(viewType){
//            TYPE_DATA -> {
//                val view = LayoutInflater.from(parent.context).inflate(R.layout.place_all_posts,parent,false)
//                TestPostViewHolder(view)
//            }
//            TYPE_PROGRESS -> {
//                Log.d("Test","프로그세스")
//                val view= LayoutInflater.from(parent.context).inflate(R.layout.progressbar,parent,false)
//                ProgressHolder(view)
//            }
//            else -> throw IllegalArgumentException("Something wrong")
//        }
//    }
//
//    inner class TestPostViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView){
//        val num=itemView.findViewById<TextView>(R.id.author)
//        val time=itemView.findViewById<TextView>(R.id.time)
//        val mainContent=itemView.findViewById<TextView>(R.id.content)
//        val heartImage=itemView.findViewById<ImageView>(R.id.like)
//        val likeCount=itemView.findViewById<TextView>(R.id.like_count)
//    }
//    inner class ProgressHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
//
//    override fun getItemCount(): Int = list.size
//
//    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//        if(holder is TestPostViewHolder){
//            holder.num.text=list[position].author.toString()
//            holder.mainContent.text=list[position].content
//            holder.time.text=list[position].timestamp
//            holder.likeCount.text=list[position].likeCount.toString()
//        }
//    }
//
//    override fun getItemViewType(position: Int): Int {
//        val viewType=list[position].content
//        return when(viewType){
//            "nothing" -> TYPE_PROGRESS
//            else -> TYPE_DATA
//        }
//    }
//}
class TestPostAdapter(val list: ArrayList<TestPost>) : RecyclerView.Adapter<TestPostAdapter.CustomViewHolder>(){
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