package com.professionalandroid.apps.androider

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment1.view.*

class PlaceFrag:Fragment(){
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view=inflater.inflate(R.layout.fragment1,container,false)
        setLocationButton(view)
        setRecyclerView1(view)
        //setButton(view)
        return view
    }
    private fun setLocationButton(view:View){
        view.local_search_btn.setOnClickListener {
            val nextIntent= Intent(requireContext(),SearchView1::class.java)
            startActivity(nextIntent)
        }
    }
    private fun setRecyclerView1(view:View){
        val list=makeList1()
        view.placeList1.layoutManager=LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        view.placeList1.setHasFixedSize(true)
        view.placeList1.adapter=ItemImageButtonAdapter(list)
    }
    private fun makeList1():ArrayList<ItemImageButton>{
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
//    private fun setButton(view: View){
//        view.search_button.setOnClickListener {
//            val context=requireContext()
//            if(context is MainActivity)
//                context.nextFragment(this,PlaceSearchFrag())
//        }
//    }
}
