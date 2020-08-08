package com.professionalandroid.apps.androider.navigation.addpost

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.professionalandroid.apps.androider.R
import com.professionalandroid.apps.androider.model.ItemDTO
import com.professionalandroid.apps.androider.model.PostDTO
import com.professionalandroid.apps.androider.model.StoreDTO
import com.professionalandroid.apps.androider.navigation.addpost.addressing.CancelItemDialogFragment
import kotlinx.android.synthetic.main.activity_addpost.*
import kotlinx.android.synthetic.main.item_selected.view.*
import kotlinx.android.synthetic.main.item_selectphoto_image.view.*

class AddPostActivity : AppCompatActivity(), CancelItemDialogFragment.NoticeDialogListener {
    val ADD_PHOTO_REQUEST = 5
    var contentWidth: Int = -1

    var itemDTO: ItemDTO? = null
    var storeDTO: StoreDTO? =null
    lateinit var uriResult: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addpost)

        btn_addpost_addstore.setOnClickListener {
            startActivity(Intent(this, SelectStoreActivity::class.java))
        }

        btn_addpost_additem.setOnClickListener {
            startActivity(Intent(this, SelectItemActivity::class.java))
        }

        btn_addpost_addphoto.setOnClickListener {
            val intent = Intent(this, SelectPhotoActivity::class.java)
            startActivityForResult(intent, ADD_PHOTO_REQUEST)
        }

        btn_addpost_cancle.setOnClickListener {
            onBackPressed()
        }

        btn_addpost_complete.setOnClickListener {
            // Upload post, Update Database
        }

        recyclerview_addpost_postimage.post {
            contentWidth = recyclerview_addpost_postimage.width
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == ADD_PHOTO_REQUEST) {
            when (resultCode) {
                Activity.RESULT_OK -> {

                    uriResult = data?.getStringArrayListExtra("imageURIs") ?: arrayListOf("")
                    recyclerview_addpost_postimage.adapter = PostImageRecyclerViewAdapter(uriResult)
                    val spanCount = if (uriResult.size == 4) 2 else uriResult.size
                    recyclerview_addpost_postimage.layoutManager =
                        GridLayoutManager(this, spanCount)
                }
                else ->
                    Toast.makeText(this, "fail", Toast.LENGTH_LONG).show()
            }
        }
    }

    inner class PostImageRecyclerViewAdapter(uriResult: ArrayList<String>) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        private val uriList = uriResult

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_selectphoto_image, parent, false)
            val width = if (itemCount == 4)
                contentWidth / 2
            else
                contentWidth / itemCount
            val height = if (itemCount == 3)
                contentWidth / 2 / 4 * 3
            else
                width / 4 * 3

            view.layoutParams = ViewGroup.LayoutParams(width, height)

            return CustomViewHolder(view)
        }

        inner class CustomViewHolder(var view: View) : RecyclerView.ViewHolder(view)

        override fun getItemCount(): Int = uriList.size

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val viewHolder = (holder as CustomViewHolder).itemView
            viewHolder.imageview_selectphoto_uncheck.visibility = ImageView.VISIBLE

            Glide.with(holder.itemView.context).load(uriList[position])
                .into(viewHolder.imageview_selectphotoitem)

            viewHolder.imageview_selectphoto_uncheck.setOnClickListener {
                imageUnchecked(uriList, position)
            }
        }
    }

    private fun imageUnchecked(uriList: ArrayList<String>, position: Int) {
        uriList.removeAt(position)
        if (uriList.size == 0)
            recyclerview_addpost_postimage.removeAllViews()
        else {
            recyclerview_addpost_postimage.adapter = PostImageRecyclerViewAdapter(uriList)
            val spanCount = if (uriList.size == 4) 2 else uriList.size
            recyclerview_addpost_postimage.layoutManager =
                GridLayoutManager(this, spanCount)
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        itemDTO = null
        storeDTO = null
        imageview_addpost_selecteditem.removeAllViews()

        when(intent?.getIntExtra("type", -1)) {
            PostDTO.ITEM -> itemDTO = intent.getParcelableExtra("resultDTO")
                ?: throw IllegalStateException("resultDTO must not be null")
            PostDTO.STORE -> storeDTO = intent.getParcelableExtra("resultDTO")
                ?: throw IllegalStateException("resultDTO must not be null")
        }

        val name = storeDTO?.name ?: itemDTO?.name ?: ""

        val view = LayoutInflater.from(this)
            .inflate(R.layout.item_selected, imageview_addpost_selecteditem, false)
        view.textview_itemselected_name.text = name
        view.btn_itemselected_cancel.setOnClickListener {
            val dialog = CancelItemDialogFragment()
            dialog.show(supportFragmentManager, "cancel")
        }

        imageview_addpost_selecteditem.addView(view)
    }

    override fun onDialogCompleteClick() {
        itemDTO = null
        storeDTO = null
        imageview_addpost_selecteditem.removeAllViews()
    }
}
