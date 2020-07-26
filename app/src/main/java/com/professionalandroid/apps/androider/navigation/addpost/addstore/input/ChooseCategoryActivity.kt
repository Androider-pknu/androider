package com.professionalandroid.apps.androider.navigation.addpost.addstore.input

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.marginStart
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.professionalandroid.apps.androider.R
import kotlinx.android.synthetic.main.activity_choosecategory.*
import kotlinx.android.synthetic.main.item_category.view.*

class ChooseCategoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choosecategory)

        btn_choosecategory_back.setOnClickListener {
            onBackPressed()
        }

        inflateContent()
    }

    private fun inflateContent() {
        val categoryList = resources.getStringArray(R.array.store_category)
        val extraList = resources.getStringArray(R.array.extra_store_category)
        val layout = layout_choosecategory_scrolllinear
        val inflater = LayoutInflater.from(this)

        val store = TextView(this)
        store.text = "음식점"
        store.textSize = 13f
        layout.addView(store)

        for (name in categoryList) {
            val view = inflater.inflate(R.layout.item_category, layout, false)
            view.textview_itemcategory_category.text = name
            layout.addView(view)
        }

        val extra = TextView(this)
        extra.text = "기타 장소"
        extra.textSize = 13f
        layout.addView(extra)

        for (name in extraList) {
            val view = inflater.inflate(R.layout.item_category, layout, false)
            view.textview_itemcategory_category.text = name
            layout.addView(view)
        }
    }
}
