package com.professionalandroid.apps.androider

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.dialog.view.*
import kotlinx.android.synthetic.main.fragment1.view.*
import kotlinx.android.synthetic.main.fragment3.view.*
import java.util.zip.Inflater

class UserFrag:Fragment(){
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view=inflater.inflate(R.layout.fragment3,container,false)
        setSearchButton(view)
        setQuestionButton(view,view.question1,true)
        setQuestionButton(view,view.question2,false)
        setImageClick(view)
        setButtonListener(view.more1)
        setButtonListener(view.more2)
        return view
    }
    private fun setSearchButton(view:View){
        view.city_search_btn.setOnClickListener {
            val nextIntent= Intent(requireContext(),SearchView2::class.java)
            startActivity(nextIntent)
        }
    }
    private fun setQuestionButton(view: View,question:Button,flag:Boolean){
        question.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())
            val num:Int = if(flag) R.layout.dialog
            else R.layout.dialog2
            val dialogView = layoutInflater.inflate(num, null)
//          val dialogText = dialogView.findViewById<TextView>(R.id.dialog_text)
//          val dialogTitle = dialogView.findViewById<TextView>(R.id.dialog_title)
            builder.setView(dialogView).show()
        }
    }
    private fun setButtonListener(btn:Button){
        btn.setOnClickListener {
            val nextIntent=Intent(requireContext(),MoreAndMore::class.java)
            startActivity(nextIntent)
        }
    }
    private fun setImageClick(view:View){
        setProfileImage(view.image1)
        setProfileImage(view.image2)
        setProfileImage(view.image3)
        setProfileImage(view.image4)
        setProfileImage(view.image5)
        setProfileImage(view.image6)
    }
    private fun setProfileImage(img:ImageView){
        img.setOnClickListener {
            val nextIntent= Intent(requireContext(),Profile::class.java)
            startActivity(nextIntent)
        }
    }
}
