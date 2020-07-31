package com.professionalandroid.apps.androider.newsfeed

import android.graphics.Color
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.professionalandroid.apps.androider.R

class KindOfFoodAdapter(val list:ArrayList<KindOfFood>) : RecyclerView.Adapter<KindOfFoodAdapter.CustomViewHolder>(){
    var pos:Int=0
    val mSelectedItems=SparseBooleanArray(0)
    var preBtn:Button? = null
    var flag=false
    interface OnCategoryClickListener{
        fun onClick(view:View,position:Int,flag:Boolean)
    }
    private lateinit var categoryClickListener: OnCategoryClickListener

    fun setCategoryClickListener(categoryClickListener: OnCategoryClickListener){
        this.categoryClickListener=categoryClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.kind_of_food,parent,false)
        mSelectedItems.put(pos,true)
        return CustomViewHolder(
            view
        )
    }

    override fun getItemCount()=list.size

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        var view=holder as CustomViewHolder
        view.cate_btn.text= list[position].item
        if(mSelectedItems.get(position))  holder.cate_btn.setTextColor(Color.parseColor("#FF4500"))
        else  holder.cate_btn.setTextColor(Color.parseColor("#000000"))
        holder.cate_btn.setOnClickListener {//여기서 SparseBooleanArray 객체가 어떠한 방식으로 작동이 되는지 잘 모르겠음.
            flag = holder.cate_btn.text=="전체"
            categoryClickListener.onClick(it,position,flag)
            if(!mSelectedItems.get(position)){
                mSelectedItems.delete(pos)
                mSelectedItems.put(position,true)
                holder.cate_btn.setTextColor(Color.parseColor("#FF4500"))
                notifyItemChanged(position)
                preBtn?.setTextColor(Color.parseColor("#000000"))
                notifyItemChanged(pos)
                pos=position
                preBtn=holder.cate_btn
            }
        }
    }
    class CustomViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val cate_btn=itemView.findViewById<Button>(R.id.category_btn)//버튼.
    }
}