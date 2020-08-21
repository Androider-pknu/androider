package com.professionalandroid.apps.androider.search.click.result

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.professionalandroid.apps.androider.R
import com.professionalandroid.apps.androider.model.StoreDTO

class SearchResultPageAdapter (private var context: Context) : PagerAdapter(){
    private var markerModelList = ArrayList<StoreDTO>()

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

    fun addItem(item: StoreDTO){
        markerModelList.add(item)
    }

    fun removeItem(){
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

        storeName.text = markerModelList[position].name
        storeCategory.text = markerModelList[position].category
        storeNumber.text = markerModelList[position].number
        storeLocation.text = markerModelList[position].address

        if(markerModelList[position].image_url==null){
            postImg.setImageResource(R.drawable.koreanfood_basic) // 기본 이미지
        } else{
            Glide.with(context).load(markerModelList[position].image_url).into(postImg)
        }

        rootView.setOnClickListener { // 뷰페이저 클릭 리스너
            val storeName = markerModelList[position].name
            //TODO SOMETHING....
        }

        container.addView(rootView,0)
        return rootView
    }
    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}













