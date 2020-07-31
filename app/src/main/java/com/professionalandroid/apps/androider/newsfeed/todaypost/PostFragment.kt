package com.professionalandroid.apps.androider.newsfeed.todaypost

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.professionalandroid.apps.androider.R
import kotlinx.android.synthetic.main.fragment_post.view.*

class PostFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view=inflater.inflate(R.layout.fragment_post, container, false)
        setBackButton(view)
        return view
    }
    private fun setBackButton(view:View){
        view.post_back.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }
}
