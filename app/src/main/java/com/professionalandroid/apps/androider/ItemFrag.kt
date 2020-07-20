package com.professionalandroid.apps.androider

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.professionalandroid.apps.androider.R
import kotlinx.android.synthetic.main.fragment1.*
import kotlinx.android.synthetic.main.fragment1.view.*
import kotlinx.android.synthetic.main.fragment2.view.*

class ItemFrag:Fragment(){
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        val view=inflater.inflate(R.layout.fragment2,container,false)
        setRecyclerView1(view)
        setRecyclerView2(view)
        return view
    }
    private fun setRecyclerView1(view: View){
        val list=makeList1()
        view.category_item.layoutManager=LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        view.category_item.setHasFixedSize(true)
        view.category_item.adapter=KindOfFoodAdapter(list)
    }
    private fun makeList1():ArrayList<KindOfFood>{
        val list= arrayListOf<KindOfFood>()
        list.add(KindOfFood("전체"))
        list.add(KindOfFood("음식/메뉴"))
        list.add(KindOfFood("카페"))
        list.add(KindOfFood("편의점"))
        list.add(KindOfFood("냉동/반조리"))
        list.add(KindOfFood("라면"))
        list.add(KindOfFood("간식/간편식"))
        list.add(KindOfFood("과자/빙과류"))
        list.add(KindOfFood("빵/떡"))
        list.add(KindOfFood("음료"))
        list.add(KindOfFood("주류"))
        list.add(KindOfFood("과일/식재료"))
        list.add(KindOfFood("요리"))
        list.add(KindOfFood("문화"))
        list.add(KindOfFood("기타"))
        return list
    }
    private fun setRecyclerView2(view:View){
        val list=makeList2()
        view.food_item.layoutManager=LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        view.food_item.setHasFixedSize(true)
        view.food_item.adapter=ItemImageButtonAdapter(list)
    }
    private fun makeList2():ArrayList<ItemImageButton>{
        val list= arrayListOf<ItemImageButton>()
//        list.add(ItemImageButton(R.drawable.image1,"1. 미친 막창","육류"))
//        list.add(ItemImageButton(R.drawable.image2,"2. 갬성 카페","카페"))
//        list.add(ItemImageButton(R.drawable.image3,"3. 닭발의 지존","육류"))
//        list.add(ItemImageButton(R.drawable.image4,"4. 마니아","육류"))
//        list.add(ItemImageButton(R.drawable.image5,"5. 카레온","식사"))
//        list.add(ItemImageButton(R.drawable.image6,"6. 장미 멘숀","소주"))
//        list.add(ItemImageButton(R.drawable.image7,"7. 해쉬","식사"))
//        list.add(ItemImageButton(R.drawable.image8,"8. 광안리 초장집","회"))
//        list.add(ItemImageButton(R.drawable.image9,"9. 엽기 떡볶이","분식"))
//        list.add(ItemImageButton(R.drawable.image10,"10. 아웃닭","호프"))
        return list
    }
}
