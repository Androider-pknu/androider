package com.professionalandroid.apps.androider.common

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.chip.Chip
import com.professionalandroid.apps.androider.R
import com.professionalandroid.apps.androider.model.StoreDTO
import com.professionalandroid.apps.androider.model.TagDTO
import com.professionalandroid.apps.androider.util.AWSRetrofit
import com.professionalandroid.apps.androider.util.Settings
import kotlinx.android.synthetic.main.fragment_store_info.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StoreInfoFragment(private val mStoreDto: StoreDTO) : Fragment(),
    View.OnClickListener {

    // lateinit var mPostDTO: PostDTO
    var mTags: MutableList<TagDTO>? = mutableListOf()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        context.theme.applyStyle(R.style.StoreInfoTheme, true)
        Settings.setWindowTranslucent(requireActivity().window, true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initTags()
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_store_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar((requireActivity() as AppCompatActivity))
        btn_add_tag.setOnClickListener(this)
    }

    override fun onDestroy() {
        Settings.setWindowTranslucent(requireActivity().window, false)
        super.onDestroy()
    }

    // add tag
    override fun onClick(v: View?) {
        Log.d("test6666666","sibar")
        store_tag_group.addView(createTag(TagDTO("테스트테스트", 2)))
    }

    private fun updateTags(tags: List<TagDTO>) {
        for (tag in tags) store_tag_group.addView(createTag(tag))
    }

    private fun createTag(tag: TagDTO): Chip {
        val chip = Chip(requireContext())
        chip.text = tag.name

        when (tag.type) {
            // 뽈레 태그
            1 -> chip.setChipBackgroundColorResource(R.color.material_amber500)
            // 사용자 태그
            2 -> chip.setChipBackgroundColorResource(R.color.material_grey300)
        }

        mTags?.add(tag)
        return chip
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_toolbar_store, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {

            }
            R.id.share_store -> {

            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initToolbar(activity: AppCompatActivity) {
        activity.setSupportActionBar(toolbar_store)

        val sab = activity.supportActionBar
        sab?.setDisplayHomeAsUpEnabled(true)
        sab?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_24)
    }

    private fun initTags() {
        val response = AWSRetrofit.getAPI().getTags(mStoreDto.id)
        response.enqueue(object : Callback<List<TagDTO>> {

            override fun onFailure(call: Call<List<TagDTO>>, t: Throwable) {
                Log.d("Test_StoreInfoTag", "f:" + t.message.toString())
            }

            override fun onResponse(call: Call<List<TagDTO>>, response: Response<List<TagDTO>>) {
                if (!response.body().isNullOrEmpty()) updateTags(response.body()!!)
            }

        })
    }

}