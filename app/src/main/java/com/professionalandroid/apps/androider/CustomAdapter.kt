package com.professionalandroid.apps.androider

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

/*
create by hakjin 16/7/2020
* */


//그리드 뷰 어댑터
class CustomAdapter : BaseAdapter {
    //var img: IntArray
    var img: ArrayList<Int>
    var con: Context
    var name: ArrayList<String>
    var location: ArrayList<String>

    private lateinit var inflaor: LayoutInflater
    constructor(img: ArrayList<Int>, con: Context, name: ArrayList<String>, location: ArrayList<String>) : super() {
        this.img = img
        this.con = con
        this.name = name
        this.location = location
        inflaor = con.getSystemService(Context.LAYOUT_INFLATER_SERVICE ) as LayoutInflater
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var holder = Holder()
        val rv: View = inflaor.inflate(R.layout.list_place,null)

        holder.tv_name=rv.findViewById(R.id.tv_hotplace_name) as TextView
        holder.tv_location=rv.findViewById(R.id.tv_hotplace_location) as TextView
        holder.iv=rv.findViewById(R.id.hot_place_img) as ImageView
        //imageView1

        holder.tv_name.text = name[position].toString()
        holder.tv_location.text = location[position].toString()
        holder.iv.setImageResource(img[position])

        return rv
    }

    override fun getItem(position: Int): Any? {
        return null
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return name.size
    }

    public class Holder{
        lateinit var tv_name: TextView
        lateinit var tv_location: TextView
        lateinit var iv: ImageView
    }
}