package com.professionalandroid.apps.androider.search.map.marker

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.professionalandroid.apps.androider.R

class LMMarkerManager(context: Context, mMap: GoogleMap) {

    private var con = context
    private var map = mMap

    private lateinit var markerRootView: View // 마커뷰
    private lateinit var btnMarker: Button  // 바커 이미지
    private  var markerList = ArrayList<Marker>()
    private var selectedMarker: Marker? = null

    fun getMarkerManager(context: Context,googleMap: GoogleMap): LMMarkerManager{
        return LMMarkerManager(context,googleMap)
    }

    fun getMarkerItem(){
        setCustomMarkerView()
        selectedMarker = null

        val list = ArrayList<LMMarkerItem>() //  마커의 위치 리스트
        for (i in 1..20) list.add(LMMarkerItem(35.113+(i*0.01),129.113,""))
        for(item in list)
            addMarker(item,false)
    }

    //뷰설정
    private fun setCustomMarkerView(){ // 해당하는 마커마다 inflate 하는 xml 파일 교체
        markerRootView = LayoutInflater.from(con).inflate(R.layout.localmaster_marker,null)
        btnMarker = markerRootView.findViewById(R.id.btn_lm_marker)
    }

    private fun addMarker(markerItem: LMMarkerItem, isSelectedMarker: Boolean): Marker { // 마커 추가
        val position = LatLng(markerItem.lat,markerItem.lon)

        if(isSelectedMarker){ // 선택된 마커
            //btnMarker.setBackgroundResource(R.drawable.ic_restaurant_white_24dp)
            btnMarker.setBackgroundColor(Color.RED)
        } else { // 선택되지 않은 마커
            //btnMarker.setBackgroundResource(R.drawable.ic_restaurant_white_24dp)
            btnMarker.setBackgroundColor(Color.BLUE)
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

    private fun addMarker(marker: Marker?, isSelectedMarker: Boolean): Marker? { // 마커 추가
        val position = marker?.position
        val temp = position?.latitude?.let { marker.title?.let { it1 ->
            LMMarkerItem(it,position.longitude,
                it1
            )
        } }
        return temp?.let { addMarker(it,isSelectedMarker) }
    }

    fun deleteMarker(){ // 마커 제거
        Log.d("hakjin","마커제거")
        if(markerList.isNotEmpty())
            for (i in markerList) i.remove()
    }
    private fun createDrawableFromView(context: Context, view: View) : Bitmap { // 뷰를 비트맵으로 변환
        val displayMetrics = DisplayMetrics()
        context as Activity
        context.windowManager.defaultDisplay.getMetrics(displayMetrics)
        view.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels)
        view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels)
        val bitmap = Bitmap.createBitmap(view.measuredWidth, view.measuredHeight, Bitmap.Config.ARGB_8888)
        val canvas =  Canvas(bitmap)
           view.draw(canvas)
    return bitmap
}

    //커스텀 마커만들기 참고

//    Drawable circleDrawable = getResources().getDrawable(R.mipmap.primarysplitter);
//    bitmapDescriptor=getMarkerIconFromDrawable(circleDrawable);
//
//
//    private BitmapDescriptor getMarkerIconFromDrawable(Drawable drawable) {
//        Canvas canvas = new Canvas();
//        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
//        canvas.setBitmap(bitmap);
//        drawable.setBounds(0, 0, (int)getResources().getDimension(R.dimen._30sdp), (int)getResources().getDimension(R.dimen._30sdp));
//        drawable.draw(canvas);
//        return BitmapDescriptorFactory.fromBitmap(bitmap);
//    }
}