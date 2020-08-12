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

    private lateinit var markerRootView: View // 마커뷰
    private lateinit var imgMarker: ImageView  // 바커 이미지
    private  var markerList = ArrayList<Marker>()
    private var markerLocationList = ArrayList<LMMarkerItem>()
    private var selectedMarker: Marker? = null

    fun getMarkerItem(list: ArrayList<LMMarkerItem>){
        setCustomMarkerView()
        selectedMarker = null
        markerLocationList = list
        for(item in markerLocationList)
            addMarker(item,false)
    }

    fun getMarkerItem(list: ArrayList<LMMarkerItem>, position: Int){ // list 는 찍힐 Marker 들의 위치정보 가지고있음
        setCustomMarkerView()
        selectedMarker = null
        markerLocationList = list
        for(item in markerLocationList)
            addMarker(item,false)
        selectedMarker = addMarker(markerLocationList[position],true)
    }

    //뷰설정
    private fun setCustomMarkerView(){ // 해당하는 마커마다 inflate 하는 xml 파일 교체
        markerRootView = LayoutInflater.from(con).inflate(R.layout.localmaster_marker,null)
        imgMarker = markerRootView.findViewById(R.id.img_sr_marker)
    }

    private fun addMarker(markerItem: LMMarkerItem, isSelectedMarker: Boolean): Marker { // 마커 추가
        val position = LatLng(markerItem.lat,markerItem.lon)
        if(isSelectedMarker){
            imgMarker.setImageResource(R.drawable.restaurants_icon)
        }
        else {
            imgMarker.setImageResource(R.drawable.marker4)
        }

        val markerOptions = MarkerOptions().title("Test").position(position).icon(
            BitmapDescriptorFactory.fromBitmap(
                createDrawableFromView(con,markerRootView)
            ))
        val marker = map.addMarker(markerOptions)
        markerList.add(marker)
        return marker
    }

    fun changedSelectedMarker(marker: Marker?){ //선택된 마커 되돌리기 ->  삭제후 다시 추가
        addMarker(selectedMarker,false)
        selectedMarker?.remove()
        if(marker!=null){
            selectedMarker = this.addMarker(marker,true)!!
            marker.remove()
        }
    }
    
    fun getSelectedMarker(): Marker? {
        return selectedMarker
    }

    private fun addMarker(marker: Marker?, isSelectedMarker: Boolean): Marker? { // 마커 추가
        val position = marker?.position
        val temp = position?.latitude?.let{
            marker.title?.let{ it1 -> LMMarkerItem(it,position.longitude)
        } }
        return temp?.let { addMarker(it,isSelectedMarker) }
    }

    fun deleteMarker(){ // 마커 제거
        if(markerList.isNotEmpty()){
            for (item in markerList)
                item.remove()
        }
        markerLocationList.clear()
        selectedMarker = null
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