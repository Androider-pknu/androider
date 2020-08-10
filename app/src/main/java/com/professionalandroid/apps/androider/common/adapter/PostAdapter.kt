package com.professionalandroid.apps.androider.common.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.professionalandroid.apps.androider.R
import com.professionalandroid.apps.androider.common.viewholder.PostViewHolder
import com.professionalandroid.apps.androider.common.viewholder.PreviewPostViewHolder
import com.professionalandroid.apps.androider.databinding.ItemPostBinding
import com.professionalandroid.apps.androider.databinding.ItemPreviewPostBinding
import com.professionalandroid.apps.androider.model.TestPostDTO
import java.lang.RuntimeException

class PostAdapter(private val mType: Int, private val mItems: MutableList<TestPostDTO>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val TYPE_PREVIEW = 1
        const val TYPE_POST = 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View?
        return when (mType) {
            TYPE_PREVIEW -> {
                view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_preview_post, parent, false)
                PreviewPostViewHolder(ItemPreviewPostBinding.bind(view))
            }
            TYPE_POST -> {
                view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_post, parent, false)
                PostViewHolder(ItemPostBinding.bind(view))
            }
            else -> throw RuntimeException("unknown ViewType")
        }
    }

    override fun getItemCount(): Int = mItems.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (mType) {
            TYPE_PREVIEW -> (holder as PreviewPostViewHolder).binding.postDTO = mItems[position]
            TYPE_POST -> (holder as PostViewHolder).binding.postDTO = mItems[position]
        }
    }
}