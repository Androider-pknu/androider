package com.professionalandroid.apps.androider.newsfeed.commonsearch

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.professionalandroid.apps.androider.MainActivity

import com.professionalandroid.apps.androider.R
import kotlinx.android.synthetic.main.activity_main.*

class PlaceSearchResult(val mainContext:MainActivity) : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view=inflater.inflate(R.layout.fragment_place_search_result, container, false)
        return view
    }
}
