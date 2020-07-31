package com.professionalandroid.apps.androider.newsfeed

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.professionalandroid.apps.androider.R
import kotlinx.android.synthetic.main.fragment_ep_more.view.back
import kotlinx.android.synthetic.main.fragment_store_page.view.*


class StorePage(val testText:String) : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view= inflater.inflate(R.layout.fragment_store_page, container, false)
        view.hint_text.text=testText
        setQuitButton(view)
        return view
    }
    private fun setQuitButton(view:View){
        view.back.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }
}
