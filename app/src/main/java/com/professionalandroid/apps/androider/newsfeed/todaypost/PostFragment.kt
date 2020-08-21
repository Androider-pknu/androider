package com.professionalandroid.apps.androider.newsfeed.todaypost

import android.os.Build
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import com.bumptech.glide.Glide
import com.professionalandroid.apps.androider.R
import com.professionalandroid.apps.androider.newsfeed.loaddata.TestPost
import com.professionalandroid.apps.androider.newsfeed.loaddata.TestPostAdapter
import kotlinx.android.synthetic.main.fragment_post.view.*

class PostFragment(private val postInfo:TestPost?) : Fragment() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view=inflater.inflate(R.layout.fragment_post, container, false)
        if(postInfo!=null) setPostInfo(view)
        setBackButton(view)
        return view
    }
    private fun setBackButton(view:View){
        view.post_back.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun setPostInfo(view:View){
        view.post_title.text = (postInfo!!.userID + "님의 포스트")
        Glide.with(requireContext()).load(postInfo.imageOfMember).into(view.post_profile_img)
        view.post_author_name.text = postInfo.userID
        view.post_author_nickname.text = postInfo.nickName
        view.post_what_time.text = TestPostAdapter.calculateTime(postInfo.timestamp)
        view.post_content.text = postInfo.content
        view.post_like_number.text = ("좋아요" + postInfo.likeCount.toString() + "개")
        if(postInfo.image!=null){
            view.post_imgs.layoutParams.height=400
            val layoutParams=LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT,
                (postInfo.image.size).toFloat())
            for(i in 0 until postInfo.image.size){
                val picture = ImageView(context)
                Glide.with(requireContext()).load(postInfo.image[i]).into(picture)
                layoutParams.gravity = Gravity.CENTER
                picture.scaleType = ImageView.ScaleType.CENTER_CROP
                picture.layoutParams = layoutParams
                view.post_imgs.addView(picture)
            }
        }
    }
}
