package com.professionalandroid.apps.androider.search.click

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.professionalandroid.apps.androider.R
import com.professionalandroid.apps.androider.newsfeed.Category

class CategoryAdapter(val categoryList: ArrayList<Category>) : RecyclerView.Adapter<CategoryAdapter.CustomViewHolder>(){

    interface OnCategoryItemClickListener{
        fun onCategoryItemClicked(view: View, position: Int)
    }

    lateinit var mListener: OnCategoryItemClickListener

    fun setOnClickListener(listener: OnCategoryItemClickListener){
        mListener = listener
    }

    //onCreateViewHolder 가 하는 역할 엑티비티에서 onCreate 와 비슷하다 플러그로 연결된 화면에 붙이는 작업을 한다

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        /*context란 엑티비테에서 담고있는 모든 정보를 말함어댑터랑 연결될 엑티비티의 모든 context를 가져옴  */
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_search_category, parent, false)
        return CustomViewHolder(
            view
        )
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    /*oncreateViewHolder로 만들어진 그 뷰를 가져다가 실제 연결해주는 작업을 함
    * 스크롤을 할때나 리스트뷰를 사용할때 onBindViewHolder가 지속적으로 호출되면서
    * 뷰에 대해서 안정적으로 모든 데이터들을 안정적으로 매칭
    * position 현재 클릭한 위치 또는 현재 바인딩 되고 있는 포지션에 대해 연동이 됨*/
    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        /*예를 들어 스크롤을 내릴때 포지션별로 연동을 계속해주어 매칭을 해준다 한번만 실행이 안되고 계속실행이됨 */
        holder.categoryName.text = categoryList[position].categoryName

        holder.categoryName.setOnClickListener {
            mListener.onCategoryItemClicked(it,position)
        }
    }
    class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val categoryName = itemView.findViewById<TextView>(R.id.btn_category) // 카테고리

    }
}












