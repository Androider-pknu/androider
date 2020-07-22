package com.professionalandroid.apps.androider

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CategoryAdapter (val categoryList: ArrayList<Category>): RecyclerView.Adapter<CategoryAdapter.CustomViewHolder>(){

    //onCreateViewHolder 가 하는 역할 엑티비티에서 onCreate와 비슷하다
    //플러그로 연결된 화면에 붙이는 작업을 한다
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryAdapter.CustomViewHolder {
        /*context란 엑티비테에서 담고있는 모든 정보를 말함
        * 어댑터랑 연결될 엑티비티의 모든 context를 가져옴  */
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_category,parent,false)
        return CustomViewHolder(view) // 커스텀뷰 홀더에 만든 view를 전달
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    /*oncreateViewHolder로 만들어진 그 뷰를 가져다가 실제 연결해주는 작업을 함
    * 스크롤을 할때나 리스트뷰를 사용할때 onBindViewHolder가 지속적으로 호출되면서
    * 뷰에 대해서 안정적으로 모든 데이터들을 안정적으로 매칭
    * position 현재 클릭한 위치 또는 현재 바인딩 되고 있는 포지션에 대해 연동이 됨*/
    override fun onBindViewHolder(holder: CategoryAdapter.CustomViewHolder, position: Int) {
        /*예를 들어 스크롤을 내릴때 포지션별로 연동을 계속해주어 매칭을 해준다
        * 한번만 실행이 안되고 계속실행이됨 */
        holder.categoryName.text = categoryList[position].categoryName
    }

    class CustomViewHolder(categoryItemView: View): RecyclerView.ViewHolder(categoryItemView){
        val categoryName = categoryItemView.findViewById<TextView>(R.id.btn_category) // 카테고리
    }

}











