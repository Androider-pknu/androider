package com.professionalandroid.apps.androider

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter

class SearchResultPageAdapter (private var context: Context) : PagerAdapter(){

    var markerModelList = ArrayList<SearchResultMarkerModel>()
    lateinit var layoutInflater: LayoutInflater
    lateinit var postImg: ImageView
    lateinit var storeName: TextView
    lateinit var storeCategory: TextView
    lateinit var storeNumber: TextView
    lateinit var storeLocation: TextView

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount(): Int {
        return markerModelList.size
    }

    fun addItem(item: SearchResultMarkerModel){ //아이템을 넣기
        markerModelList.add(item)
    }
    fun removeItem(){   // 리스트를 초기화
        markerModelList.clear()
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        layoutInflater = LayoutInflater.from(context)

        val rootView = layoutInflater.inflate(R.layout.item_searchresult_marker,container,false)

        postImg = rootView.findViewById(R.id.img_searchresult_marker_postImg)
        storeName = rootView.findViewById(R.id.tv_searchresult_marker_postName)
        storeCategory = rootView.findViewById(R.id.tv_searchresult_marker_category)
        storeNumber = rootView.findViewById(R.id.tv_searchresult_marker_phoneNumber)
        storeLocation = rootView.findViewById(R.id.tv_searchresult_marker_address)

        postImg.setImageResource(markerModelList[position].postImg)
        storeName.text = markerModelList[position].storeName
        storeCategory.text = markerModelList[position].storeCategory
        storeNumber.text = markerModelList[position].stroePhoneNumber
        storeLocation.text = markerModelList[position].stroeLocation

        rootView.setOnClickListener { // 뷰페이저 클릭 리스너
            if(position == 0)
                Log.d("hakjin","searchresut - 0번 페이지 선택")
            if(position == 1)
                Log.d("hakjin","searchresut - 1번 페이지 선택")
            if(position == 2)
                Log.d("hakjin","searchresut - 2번 페이지 선택")
        }

        container.addView(rootView,0)
        return rootView
    }
    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

}













