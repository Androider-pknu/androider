package com.professionalandroid.apps.androider

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.professionalandroid.apps.androider.navigation.NewsFeedFragment
import kotlinx.android.synthetic.main.fragment1.view.*
import kotlinx.android.synthetic.main.fragment_category_all.view.*

class CategoryAll(): Fragment(){
    var todayPost=PostFragment()
    var storePage=StorePage("가게 정보")
    var itemAdapter=ItemImageButtonAdapter(makeList())
    var itemPage=StorePage("전체")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        val view=inflater.inflate(R.layout.fragment_category_all,container,false)
        setRecyclerView(view)
        setTodayPost(view)
        //view.test.text = testText
        return view
    }
    private fun moveToFragment(newFragment:Fragment){
        requireActivity().supportFragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.start_more_view,R.anim.left_view,R.anim.right_view,R.anim.end_more_view)
            .addToBackStack(null).add(R.id.layout_main_content,newFragment).hide(NewsFeedFragment.thisFragment).commit()
    }
    private fun setRecyclerView(view:View){
        view.food_item.layoutManager= LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL,false)
        view.food_item.setHasFixedSize(true)
        itemAdapter.setItemImageClickListener(object: ItemImageButtonAdapter.OnItemImageClickListener{
            override fun onClick(view:View,position:Int){
                moveToFragment(itemPage)
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
    private fun setTodayPost(view:View){
        setTodayPostImgListener(view)
        setTodayPostContentsListener(view)
        setTodayPostStoreListener(view)
        setTodayPostHeartListener(view)
        setCommentListener(view)
    }
    private fun setTodayPostImgListener(view:View){
        view.i_today_post_img.setOnClickListener {
            moveToFragment(todayPost)
        }
    }
    private fun setTodayPostContentsListener(view:View){
        view.i_today_post_contents.setOnClickListener {
            moveToFragment(todayPost)
        }
    }
    private fun setTodayPostStoreListener(view:View){
        view.i_today_post_store.setOnClickListener {
            moveToFragment(storePage)
        }
    }
    private fun setTodayPostHeartListener(view:View){
        var flag=true
        view.i_heart.setOnClickListener {
            var stringNum=view.i_number_heart.text as String
            flag = if(flag) {
                view.i_heart.setBackgroundResource(R.drawable.selected_heart)
                view.i_number_heart.text=((stringNum.toInt()+1).toString())
                false
            } else {
                view.i_heart.setBackgroundResource(R.drawable.not_selected_heart)
                view.i_number_heart.text=((stringNum.toInt()-1).toString())
                true
            }
        }
    }
    private fun setCommentListener(view:View){
        view.i_comment.setOnClickListener {
            moveToFragment(todayPost)
        }
    }
}