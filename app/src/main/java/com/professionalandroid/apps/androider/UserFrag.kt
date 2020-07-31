package com.professionalandroid.apps.androider

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.professionalandroid.apps.androider.navigation.NewsFeedFragment
import kotlinx.android.synthetic.main.fragment3.view.*

class UserFrag():Fragment(){
    //var searchFragment=UserSearchFrag(newsFeedView)
    companion object{
        lateinit var local_search:Button
    }
    private val profileFragment=ProfileFragment()
    val exMoreFragment=ExploreMoreFragment()
    val socialMoreFragment=SocialMoreFragment()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view=inflater.inflate(R.layout.fragment3,container,false)
        local_search=view.city_search_btn
        setCitySearchButton(view)
        //setSearchButton(view)
        setDialogButton(view,view.question1,true)
        setDialogButton(view,view.question2,false)
        setLayoutClick(view.ex_first)
        setLayoutClick(view.ex_second)
        setLayoutClick(view.ex_third)
        setLayoutClick(view.social_first)
        setLayoutClick(view.social_second)
        setLayoutClick(view.social_third)
        setMoreButtonListener(view.more1,1)
        setMoreButtonListener(view.more2,2)
        return view
    }
    private fun moveToFragment(newFragment:Fragment){
        requireActivity().supportFragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.start_more_view,R.anim.left_view,R.anim.right_view,R.anim.end_more_view)
            /*프래그먼트의 전환 애니메이션은 setCustomAnimations 를 사용함. 첫 번째, 두 번째 인자는 새 프래그먼트로 인해 전환이 이루어졌을 때 사용되고,
        세 번째, 네 번째 인자는 새 프래그먼트에서 뒤로 갈 때 사용되는 애니메이션.*/
            .addToBackStack(null).add(R.id.layout_main_content,newFragment).hide(NewsFeedFragment.thisFragment).commit()
    }
    private fun setCitySearchButton(view:View){
        view.city_search_btn.setOnClickListener {
            val nextIntent= Intent(requireContext(),UserSelectLocal::class.java)
            startActivity(nextIntent)
        }
    }
    private fun setDialogButton(view: View,question:Button,flag:Boolean){
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
    private fun setMoreButtonListener(btn:Button,num:Int){
        btn.setOnClickListener{
            if(num==1)moveToFragment(exMoreFragment)
            else moveToFragment(socialMoreFragment)
        }
    }
    private fun setLayoutClick(layout: ConstraintLayout){
        layout.setOnClickListener {
            moveToFragment(profileFragment)
        }
    }
//    private fun setSearchButton(view: View){
//        view.user_search_button.setOnClickListener {
//            //requireActivity().supportFragmentManager.beginTransaction().add(R.id.main_frame,fragment).hide(this).commit()
//            //이게 뭘 의미하는지 모르겟음.
//            requireActivity().supportFragmentManager.beginTransaction().addToBackStack(null)
//                .add(R.id.main_frame,searchFragment).hide(this).commit()
//            requireActivity().navigation_main_bottom.visibility=View.GONE//네비게이션 바를 잠시 없애는 것.(공간 차지 o)
//            //View.INVISIBLE - View 를 감춤(공간 차지 x) View.VISIBLE - View 를 보여줌(공간 차지 o)
//        }
//    }
//    override fun onPause() {
//        if(flag){//서치뷰가 켜져있는 상태.
//            requireActivity().supportFragmentManager.beginTransaction().remove(fragment).commit()
//            flag=false
//        }
//        super.onPause()
//    }
//
//    override fun onStop() {
//        super.onStop()
//    }
//
//    override fun onDetach() {
//        super.onDetach()
//    }
}
