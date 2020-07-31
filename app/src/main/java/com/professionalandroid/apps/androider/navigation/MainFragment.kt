package com.professionalandroid.apps.androider.navigation

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.inflate
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.professionalandroid.apps.androider.*
import kotlinx.android.synthetic.main.fragment_newsfeed.*
import kotlinx.android.synthetic.main.fragment_newsfeed.view.*

class MainFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return LayoutInflater.from(activity).inflate(R.layout.fragment_main, container, false)
    }


}