package com.professionalandroid.apps.androider.navigation.addpost

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.professionalandroid.apps.androider.R
import com.professionalandroid.apps.androider.model.ItemDTO
import com.professionalandroid.apps.androider.model.PostDTO
import com.professionalandroid.apps.androider.model.StoreDTO
import com.professionalandroid.apps.androider.navigation.addpost.addressing.CancelItemDialogFragment
import com.professionalandroid.apps.androider.util.AWSRetrofit
import kotlinx.android.synthetic.main.activity_addpost.*
import kotlinx.android.synthetic.main.item_selected.view.*
import kotlinx.android.synthetic.main.item_selectphoto_image.view.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class AddPostActivity : AppCompatActivity(), CancelItemDialogFragment.NoticeDialogListener {
    val ADD_PHOTO_REQUEST = 5
    var contentWidth: Int = -1

    var itemDTO: ItemDTO? = null
    var storeDTO: StoreDTO? = null
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
            val retrofitAPI = AWSRetrofit.getAPI()
/*
            TODO(get author id from current login user info)
            val author_id = intent.getIntExtra("author_id", -1)
*/
            val content = textfield_addpost_postdescription.text.toString()
            val id = storeDTO?.id ?: itemDTO?.id ?: 0
            val type =
                if (storeDTO != null) PostDTO.STORE else if (itemDTO != null) PostDTO.ITEM else 0
            val imageList = arrayListOf<MultipartBody.Part>()
            for (uri in uriResult) {
                val file = File(uri)
                val fileName =
                    "post-image-[${SimpleDateFormat("yyyy-MM-dd-HH:mm:ss", Locale.getDefault()).format(Date())}]-${file.name}"
                Log.d(this::class.java.simpleName, fileName)
                val requestBody = RequestBody.create(MediaType.parse("image/*"), file)
                val part = MultipartBody.Part.createFormData("uploaded_file[]", fileName, requestBody)
                imageList.add(part)
            }

            val call = retrofitAPI.addPost(1, content, id, type, imageList) // TODO(author_id 1 is temp value)
            call.enqueue(object : Callback<String> {
                override fun onFailure(call: Call<String>, t: Throwable) {
                    Log.d(this::class.java.simpleName, "Add Post Fail")
                    Log.d(this::class.java.simpleName, t.message)
                }

                override fun onResponse(call: Call<String>, response: Response<String>) {
                    if (response.isSuccessful) {
                        Log.d(this::class.java.simpleName, "Add Post Success")
                        val result = response.body()
                        Toast.makeText(this@AddPostActivity, result, Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        Log.d(this::class.java.simpleName, "Add Post Not Success")
                    }
                }
            })
        }

        recyclerview_addpost_postimage.post {
            contentWidth = recyclerview_addpost_postimage.width
        }

        textfield_addpost_postdescription.addTextChangedListener(CompleteBtnChecker())
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

        when (intent?.getIntExtra("type", -1)) {
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

    open inner class CompleteBtnChecker() : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            if (s.isEmpty()) {
                val color = ContextCompat.getColor(applicationContext, R.color.gray)
                btn_addpost_complete.setTextColor(color)
                btn_addpost_complete.isEnabled = false
            } else {
                val color = ContextCompat.getColor(applicationContext, R.color.blue_default)
                btn_addpost_complete.setTextColor(color)
                btn_addpost_complete.isEnabled = true
            }
        }
    }
}
