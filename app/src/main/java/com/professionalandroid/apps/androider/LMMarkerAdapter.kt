package com.professionalandroid.apps.androider

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter

class LMMarkerAdapter(private var context: Context) : PagerAdapter(){

    var localMarkerModels = ArrayList<LocalMasterMarkerModel>()
    lateinit var layoutInflater: LayoutInflater
    lateinit var postImg: ImageView
    lateinit var userIcon: ImageView
    lateinit var postContent: TextView
    lateinit var storeName: TextView

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount(): Int {
        return localMarkerModels.size
    }

    fun addItem(item: LocalMasterMarkerModel){
        localMarkerModels.add(item)
    }

    fun removeItem(){
        localMarkerModels.clear()
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        layoutInflater = LayoutInflater.from(context)
        val rootView = layoutInflater.inflate(R.layout.item_localmaster_marker,container,false)

        postImg = rootView.findViewById(R.id.img_local_master_postImage)
        userIcon = rootView.findViewById(R.id.img_local_master_userIcon)
        postContent = rootView.findViewById(R.id.tv_local_master_marker_content)
        storeName = rootView.findViewById(R.id.tv_local_master_marker_storeName)

        postImg.setImageResource(localMarkerModels[position].storeImg)
        userIcon.setImageResource(localMarkerModels[position].userIcon)
        postContent.text = localMarkerModels[position].postContent
        storeName.text = localMarkerModels[position].storeName

        rootView.setOnClickListener { // 뷰페이저 클릭 리스너
            if(position == 0)
                Log.d("hakjin","0번 페이지 선택")
            if(position == 1)
                Log.d("hakjin","1번 페이지 선택")
            if(position == 2)
                Log.d("hakjin","2번 페이지 선택")
        }

        container.addView(rootView,0)
        return rootView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}