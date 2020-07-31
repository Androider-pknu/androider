package com.professionalandroid.apps.androider

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.professionalandroid.apps.androider.navigation.NewsFeedFragment
import kotlinx.android.synthetic.main.fragment_category_all.view.*
import kotlinx.android.synthetic.main.fragment_category_other.view.*
import kotlinx.android.synthetic.main.fragment_category_other.view.food_item

class CategoryOther(var testText:String):Fragment(){
    val thisFragment=this
    var itemPage=StorePage("기타 등등")
    val itemAdapter=ItemImageButtonAdapter(makeList())
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        val view=inflater.inflate(R.layout.fragment_category_other,container,false)
        view.test.text=testText
        setRecyclerView(view)
        return view
    }
    private fun setRecyclerView(view:View){
        view.food_item.layoutManager=
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL,false)
        view.food_item.setHasFixedSize(true)
        itemAdapter.setItemImageClickListener(object :ItemImageButtonAdapter.OnItemImageClickListener{
            override fun onClick(view: View, position: Int) {
                requireActivity().supportFragmentManager.beginTransaction()
                    .setCustomAnimations(R.anim.start_more_view,R.anim.left_view,R.anim.right_view,R.anim.end_more_view)
                    .addToBackStack(null).add(R.id.layout_main_content,itemPage).hide(NewsFeedFragment.thisFragment).commit()
            }
        })
        view.food_item.adapter=itemAdapter
    }
    private fun makeList():ArrayList<ItemImageButton>{
        val list= arrayListOf<ItemImageButton>()
        list.add(ItemImageButton(R.drawable.bread,"1. 미친 막창","육류"))
        list.add(ItemImageButton(R.drawable.bread,"2. 갬성 카페","카페"))
        list.add(ItemImageButton(R.drawable.bread,"3. 닭발의 지존","육류"))
        list.add(ItemImageButton(R.drawable.bread,"4. 마니아","육류"))
        list.add(ItemImageButton(R.drawable.bread,"5. 카레온","식사"))
        list.add(ItemImageButton(R.drawable.bread,"6. 장미 멘숀","소주"))
        list.add(ItemImageButton(R.drawable.bread,"7. 해쉬","식사"))
        list.add(ItemImageButton(R.drawable.bread,"8. 광안리 초장집","회"))
        list.add(ItemImageButton(R.drawable.bread,"9. 엽기 떡볶이","분식"))
        list.add(ItemImageButton(R.drawable.bread,"10. 아웃닭","호프"))
        return list
    }
}