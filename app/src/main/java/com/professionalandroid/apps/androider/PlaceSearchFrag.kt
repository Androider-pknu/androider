package com.professionalandroid.apps.androider

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.place_search.view.*

class PlaceSearchFrag() :Fragment(){
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view=inflater.inflate(R.layout.place_search,container,false)
        //setCancelButton(view)
        return view
    }
    private fun setCancelButton(view:View){
        view.cancel.setOnClickListener {

        }
    }
}