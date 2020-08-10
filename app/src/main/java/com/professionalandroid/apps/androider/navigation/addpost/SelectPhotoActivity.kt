package com.professionalandroid.apps.androider.navigation.addpost

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore.Images
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.professionalandroid.apps.androider.R
import com.professionalandroid.apps.androider.util.READ_EXTERNAL_STORAGE_REQUEST
import kotlinx.android.synthetic.main.activity_selectphotoactivity.*
import kotlinx.android.synthetic.main.item_selectphoto_image.view.*


class SelectPhotoActivity : AppCompatActivity() {
    private val checkedList = arrayListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selectphotoactivity)

        if (storagePermissionCheck()) {
            recyclerview_selectphoto.adapter = SelectPhotoRecyclerViewAdapter()
            recyclerview_selectphoto.layoutManager = GridLayoutManager(this, 3)
        }

        btn_selectphoto_cancel.setOnClickListener {
            onBackPressed()
        }

        btn_selectphoto_complete.setOnClickListener {
            val intent = Intent()
            for (s in checkedList) {
                Log.d(this::class.java.simpleName, "URI: $s")
            }
            intent.putStringArrayListExtra("imageURIs", checkedList)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }

    private fun storagePermissionCheck(): Boolean {
        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                READ_EXTERNAL_STORAGE_REQUEST
            )
        } else
            return true
        return false
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            READ_EXTERNAL_STORAGE_REQUEST -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    recyclerview_selectphoto.adapter = SelectPhotoRecyclerViewAdapter()
                    recyclerview_selectphoto.layoutManager = GridLayoutManager(this, 3)

                } else {
                    Toast.makeText(this, "fail", Toast.LENGTH_SHORT).show()
                }
            }
            else -> Toast.makeText(this, "fail", Toast.LENGTH_SHORT).show()
        }
    }

    inner class SelectPhotoRecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        private val uriList = getImageUriList()
        private val textViewList = mutableListOf<TextView>()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_selectphoto_image, parent, false)
            val size = resources.displayMetrics.widthPixels / 3
            view.layoutParams = ViewGroup.LayoutParams(size, size)

            return CustomViewHolder(view)
        }

        inner class CustomViewHolder(var view: View) : RecyclerView.ViewHolder(view)

        override fun getItemCount(): Int = uriList.size

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val viewHolder = (holder as CustomViewHolder).itemView

            Glide.with(holder.itemView.context).load(uriList[position])
                .apply(RequestOptions().centerCrop()).into(viewHolder.imageview_selectphotoitem)

            viewHolder.setOnClickListener {
                onClick(viewHolder, viewHolder.textview_selectphotoitem_check, position)
            }
        }

        private fun onClick(
            view: View,
            textView: TextView,
            position: Int
        ) {
            if (uriList[position] !in checkedList && checkedList.size < 4) {
                checkedList.add(uriList[position])
                textViewList.add(textView)
                view.layout_selectphotoitem_check.visibility = FrameLayout.VISIBLE
                view.textview_selectphotoitem_check.text = (checkedList.lastIndex + 1).toString()
            } else if (uriList[position] in checkedList) {
                val removeIndex = checkedList.indexOf(uriList[position])
                view.layout_selectphotoitem_check.visibility = FrameLayout.INVISIBLE
                checkedList.removeAt(removeIndex)
                textViewList.removeAt(removeIndex)

                for ((index, tv) in textViewList.withIndex()) {
                    tv.text = (index + 1).toString()
                }
            }
        }
    }

    private fun getImageUriList(): MutableList<String> {
        val uri = Images.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(Images.ImageColumns.DATA)
        val cursor = this.contentResolver.query(uri, projection, null, null, null)
        val uriList = mutableListOf<String>()

        Log.d("MediaStore test", cursor?.columnCount.toString())
        Log.d("MediaStore test", cursor?.count.toString())

        cursor?.moveToFirst()
        do {
            Log.d("MediaStore test", cursor?.position.toString())
            val path = cursor?.getString(cursor.getColumnIndex(Images.Media.DATA))
            Log.d("MediaStore test", path)
            uriList.add(path!!)
        } while (cursor?.moveToNext() == true)
        cursor.close()

        return uriList
    }
}