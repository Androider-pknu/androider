package com.professionalandroid.apps.androider.search.map.marker

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.*
import com.professionalandroid.apps.androider.R

class LMMarkerManager(context: Context, mMap: GoogleMap) {

    private var con = context
    private var map = mMap

    private lateinit var markerRootView: View
    private lateinit var imgMarker: ImageView
    private  var markerList = ArrayList<Marker>()
    private var markerLocationList = ArrayList<LMMarkerItem>()
    private var selectedMarker: Marker? = null

    private var hashMap =  HashMap<Int,Int>() // Same Location Marker Connection
    private var countList = ArrayList<Int>() // Same Location Marker Count
    private var boolean = ArrayList<Boolean>()

    private fun markerClustering(list: ArrayList<LMMarkerItem>){
        for(i in list.indices){
            boolean.add(true)
            countList.add(1)
        }

        for (i in list.indices) {
            if (boolean[i]) {
                for (j in i + 1 until list.size) {
                    if (list[i].lat == list[j].lat && list[i].lon == list[j].lon) {
                        hashMap[j] = i // Key, Value
                        countList[i]++
                        boolean[j] = false
                    }
                }
            }
        }
    }

    fun getMarkerItem(list: ArrayList<LMMarkerItem>){
        markerClustering(list)
        setCustomMarkerView()
        selectedMarker = null
        markerLocationList = list
        for(index in markerLocationList.indices)
            addMarker(markerLocationList[index],false,boolean[index],index)
    }

    fun getMarkerItem(list: ArrayList<LMMarkerItem>, position: Int){ // list 는 찍힐 Marker 들의 위치정보 가지고있음
        markerClustering(list)
        setCustomMarkerView()
        selectedMarker = null
        markerLocationList = list
        for(item in markerLocationList.indices)
            addMarker(markerLocationList[item],false,boolean[item],item)

        selectedMarker = if(!boolean[position])
            addMarker(markerLocationList[hashMap[position]!!],true,boolean[hashMap[position]!!],hashMap[position]!!)
        else
            addMarker(markerLocationList[position],true,boolean[position],position)
    }

    private fun setCustomMarkerView(){ // 해당하는 마커마다 inflate 하는 xml 파일 교체
        markerRootView = LayoutInflater.from(con).inflate(R.layout.localmaster_marker,null)
        imgMarker = markerRootView.findViewById(R.id.img_sr_marker)
    }

    private fun addMarker(markerItem: LMMarkerItem, isSelectedMarker: Boolean, markerFlag: Boolean,index: Int): Marker? { // 테스트용
        return if(!markerFlag)
            null
        else {
            val position = LatLng(markerItem.lat, markerItem.lon)
            changeMarkerImage(isSelectedMarker, index)
            val markerOptions = MarkerOptions().title("Test").position(position).icon(
                BitmapDescriptorFactory.fromBitmap(
                    createDrawableFromView(con, markerRootView)
                )
            )
            val marker = map.addMarker(markerOptions)
            markerList.add(marker)
            marker
        }
    }

    fun changedSelectedMarker(marker: Marker?){ //선택된 마커 되돌리기 ->  삭제후 다시 추가
        addMarker(selectedMarker,false)
        selectedMarker?.remove()
        if(marker!=null){
            selectedMarker = this.addMarker(marker,true)!!
            marker.remove()
        }
    }

    private fun addMarker(marker: Marker?, isSelectedMarker: Boolean): Marker? { // 마커 추가 ( 선택 <-> 미 선택)
        var countIndex = 0
        for (index in markerLocationList.indices){
            if(marker?.position?.latitude == markerLocationList[index].lat && marker.position?.longitude == markerLocationList[index].lon) {
                countIndex = index
                break
            }
        }
        val position = marker?.position
        val temp = position?.latitude?.let{
            marker.title?.let{ it1 -> LMMarkerItem(it,position.longitude)
            } }
        return temp?.let { addMarker(it,isSelectedMarker,boolean[countIndex],countIndex) }
    }

    fun deleteMarker(){ // 마커 제거
        if(markerList.isNotEmpty()){
            for (item in markerList)
                item.remove()
        }
        markerLocationList.clear()
        selectedMarker = null

        boolean.clear()
        countList.clear()
    }

    private fun changeMarkerImage(isSelectedMarker: Boolean, index: Int){ // MarkerImage Change
        when(countList[index]){
            1 -> {
                if (isSelectedMarker) {
                    imgMarker.setImageResource(R.drawable.restaurants_icon)
                } else {
                    imgMarker.setImageResource(R.drawable.marker4)
                }
            }
            2 -> {
                if (isSelectedMarker) {
                    imgMarker.setImageResource(R.drawable.s_mapmarker2)
                } else {
                    imgMarker.setImageResource(R.drawable.mapmarker2)
                }
            }
            3 -> {
                if (isSelectedMarker) {
                    imgMarker.setImageResource(R.drawable.s_mapmarker3)
                } else {
                    imgMarker.setImageResource(R.drawable.mapmarker3)
                }
            }
            4 -> {
                if (isSelectedMarker) {
                    imgMarker.setImageResource(R.drawable.s_mapmarker4)
                } else {
                    imgMarker.setImageResource(R.drawable.mapmarker4)
                }
            }
            5 -> {
                if (isSelectedMarker) {
                    imgMarker.setImageResource(R.drawable.s_mapmarker4)
                } else {
                    imgMarker.setImageResource(R.drawable.mapmarker4)
                }
            }
            else -> { // 추가예정
                if (isSelectedMarker) {
                    imgMarker.setImageResource(R.drawable.s_mapmarker4)
                } else {
                    imgMarker.setImageResource(R.drawable.mapmarker4)
                }
            }
        }
    }

    private fun createDrawableFromView(context: Context, view: View): Bitmap { // 뷰를 비트맵으로 변환
        val displayMetrics = DisplayMetrics()
        context as Activity
        context.windowManager.defaultDisplay.getMetrics(displayMetrics)
        view.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels)
        view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels)
        val bitmap = Bitmap.createBitmap(view.measuredWidth, view.measuredHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        view.draw(canvas)
        return bitmap
    }
}