package com.professionalandroid.apps.androider.newsfeed.loaddata

import android.content.Context
import android.icu.text.SimpleDateFormat
import android.os.Build
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.professionalandroid.apps.androider.R
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.collections.ArrayList

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
class TestPostAdapter(val list: ArrayList<TestPost>, val context: Context) : RecyclerView.Adapter<TestPostAdapter.CustomViewHolder>(){
    companion object{
        @RequiresApi(Build.VERSION_CODES.O)
        fun calculateTime(time: String) :String {
            val now=LocalDateTime.now()
            val formatter=DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            val formatted=now.format(formatter)
            val format=SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val nowDate=format.parse(formatted.toString())
            val localDate=format.parse(time)
            val diff=nowDate.time-localDate.time
            val diffStr=(diff/(24*60*60*1000)).toString()
            if(diffStr!="0") {
                val num=diffStr.toInt()
                return if(num<=29) num.toString()+"일 전"
                else if(num in 30..364) (num/30) .toString()+"달 전"
                else (num/365).toString() + "년 전"
            }
            else {
                return if(diff/1000 < 60) "방금"
                else if(diff/60000 in 1..59) (diff/60000).toString()+"분 전"
                else (diff/3600000).toString()+ "시간 전"
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.place_all_posts,parent,false)
        return CustomViewHolder(view)
    }
    override fun getItemCount()=list.size

    interface OnPostClickListener{
        fun onClick(view:View,position: Int)
    }

    private lateinit var postClickListener : OnPostClickListener

    fun setPostClickListener(postClickListener: OnPostClickListener){
        this.postClickListener=postClickListener
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.author.text=list[position].userID
        holder.mainContent.text=list[position].content
        holder.time.text=calculateTime(list[position].timestamp)
        holder.likeCount.text=list[position].likeCount.toString()
        holder.commentCount.text=list[position].commentCount.toString()
        holder.nickname.text=list[position].nickName
        if(list[position].image!=null) insertImage(list[position].image!!,holder)
        if(list[position].imageOfMember!=null) Glide.with(context).load(list[position].imageOfMember).into(holder.profileImg)
        else holder.profileImg.setImageResource(R.drawable.selected_heart)
        holder.postLayout.setOnClickListener {
            postClickListener.onClick(it,position)
        }
    }
    private fun insertImage(imgList:ArrayList<String>,holder:CustomViewHolder){

            holder.imageLayout.layoutParams.height = 400
            val layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT, (imgList.size).toFloat()
            )
            for (i in 0 until imgList.size) {
                val picture = ImageView(context)
                Glide.with(context).load(imgList[i]).into(picture)
                layoutParams.gravity = Gravity.CENTER
                picture.scaleType = ImageView.ScaleType.CENTER_CROP
                picture.layoutParams = layoutParams
                holder.imageLayout.addView(picture)
            }
//        holder.imageLayout.layoutParams.height=400
//        holder.imageLayout.rowCount=1
//        holder.imageLayout.columnCount=imgList.size
//        //val layoutParams=LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT)
//        for(i in 0 until imgList.size){
//            val picture = ImageView(context)
//            val grid=GridLayout.LayoutParams(GridLayout.spec(0,1),GridLayout.spec(i,1))
//            Glide.with(context).load(imgList[i]).into(picture)
//            grid.setGravity(Gravity.CENTER)
//            picture.scaleType = ImageView.ScaleType.CENTER_CROP
//            //picture.layoutParams = layoutParams
//            holder.imageLayout.addView(picture,grid)
//        }
    }
    class CustomViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val author = itemView.findViewById<TextView>(R.id.user_name)
        val nickname=itemView.findViewById<TextView>(R.id.user_nickname)
        val time=itemView.findViewById<TextView>(R.id.when_post)
        val mainContent=itemView.findViewById<TextView>(R.id.content)
        val heartImage=itemView.findViewById<ImageView>(R.id.post_heart)
        val likeCount=itemView.findViewById<TextView>(R.id.post_heart_number)
        val imageLayout=itemView.findViewById<LinearLayout>(R.id.for_image_layout)
        val profileImg=itemView.findViewById<ImageView>(R.id.user_profile_image)
        val commentCount=itemView.findViewById<TextView>(R.id.post_comment_number)
        val postLayout=itemView.findViewById<ConstraintLayout>(R.id.pre_post)
    }
//    @RequiresApi(Build.VERSION_CODES.O)
//    fun calculateTime(time:String) : String{
//        val now=LocalDateTime.now()
//        val formatter=DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
//        val formatted=now.format(formatter)
//        val format=SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
//        val nowDate=format.parse(formatted.toString())
//        val localDate=format.parse(time)
//        val diff=nowDate.time-localDate.time
//        val diffStr=(diff/(24*60*60*1000)).toString()
//        if(diffStr!="0") {
//            val num=diffStr.toInt()
//            return if(num<=29) num.toString()+"일 전"
//            else if(num in 30..364) (num/30) .toString()+"달 전"
//            else (num/365).toString() + "년 전"
//        }
//        else {
//            return if(diff/1000 < 60) "방금"
//            else if(diff/60000 in 1..59) (diff/60000).toString()+"분 전"
//            else (diff/3600000).toString()+ "시간 전"
//        }
//    }
}