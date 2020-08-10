package com.professionalandroid.apps.androider.common

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.professionalandroid.apps.androider.R
import com.professionalandroid.apps.androider.common.adapter.PostAdapter
import com.professionalandroid.apps.androider.model.MemberDTO
import com.professionalandroid.apps.androider.model.TestPostDTO
import com.professionalandroid.apps.androider.model.StoreDTO
import com.professionalandroid.apps.androider.model.TagDTO
import com.professionalandroid.apps.androider.util.AWSRetrofit
import com.professionalandroid.apps.androider.util.Settings
import kotlinx.android.synthetic.main.fragment_store_info.*
import kotlinx.android.synthetic.main.fragment_store_info.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StoreInfoFragment(private val mStoreDTO: StoreDTO) : Fragment(),
    View.OnClickListener {

    var mPostDTO: MutableList<TestPostDTO>? = mutableListOf()
    var mAddTags: MutableList<TagDTO>? = mutableListOf()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        context.theme.applyStyle(R.style.StoreTheme, true)
        Settings.setWindowTranslucent(requireActivity().window, true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_store_info, container, false)
        initUI(rootView)
        setHasOptionsMenu(true)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar((requireActivity() as AppCompatActivity))
        val adapter: PostAdapter

        initTestData()

        if (!mPostDTO.isNullOrEmpty()) {
            adapter = PostAdapter(PostAdapter.TYPE_PREVIEW, mPostDTO!!)
            rv_store_post.apply {
                this.adapter = adapter
                layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            }
        }

        btn_add_tag.setOnClickListener(this)
    }

    private fun initTestData() {
        for (i in 1..5)
            mPostDTO?.add(
                TestPostDTO(
                    MemberDTO(i, "oseong", "123", "oseong@test.com", "null", "${i}성"),
                    mStoreDTO,
                    null,
                    "10일 제${i}호 태풍 '장미'의 북상으로 태풍주의보가 발효된 부산지역에는 점차 빗방울이 거세지면서, 긴장감이 고조되고 있다.\n" +
                            "부산기상청에 따르면, 제5호 태풍 '장미'가 이날 오후 1시 기준 통영 남남서쪽 약 119km 해상에서 시속 51km로 북북동진하고 있다.",
                    20,
                    30,
                    1,
                    1,
                    "${i}일"
                )
            )
    }

    @SuppressLint("ResourceAsColor")
    private fun initUI(view: View) {
        if (mPostDTO.isNullOrEmpty())
            view.btn_noting_post.visibility = View.GONE
        view.toolbar_store.title = mStoreDTO.name
        view.tv_store_category.text = mStoreDTO.category
        view.tv_store_address.text = mStoreDTO.address
        if (!mStoreDTO.number.isNullOrBlank()) {
            view.tv_store_number.text = mStoreDTO.number
            view.tv_store_number.setTextColor(R.color.black)
            view.tv_store_number.isEnabled = false
        }
        initTags()
    }

    private fun initToolbar(activity: AppCompatActivity) {
        activity.setSupportActionBar(toolbar_store)

        val sab = activity.supportActionBar
        sab?.setDisplayHomeAsUpEnabled(true)
        sab?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_24)
    }

    private fun initTags() {
        val response = AWSRetrofit.getAPI().getTags(mStoreDTO.id)
        response.enqueue(object : Callback<List<TagDTO>> {

            override fun onFailure(call: Call<List<TagDTO>>, t: Throwable) {
                Log.d("Test_storeTag", "f:" + t.message.toString())
            }

            override fun onResponse(call: Call<List<TagDTO>>, response: Response<List<TagDTO>>) {
                if (!response.body().isNullOrEmpty()) updateTags(response.body()!!)
            }
        })
    }

    // add tag
    override fun onClick(v: View?) {
        Log.d("test6666666", "sibar")
        store_tag_group.addView(createTag(TagDTO("테스트", 2)))
    }

    private fun updateTags(tags: List<TagDTO>) {
        for (tag in tags) store_tag_group.addView(createTag(tag))
    }

    private fun createTag(tag: TagDTO): Chip {
        val chip = Chip(requireContext())
        chip.text = tag.name
        chip.chipStrokeWidth = 4f
        chip.setEnsureMinTouchTargetSize(false)
        chip.setChipBackgroundColorResource(R.color.white)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            when (tag.type) {
                1 -> { // 뽈레 태그
                    chip.setChipStrokeColorResource(R.color.material_amber500)
                    chip.setTextColor(resources.getColor(R.color.material_amber500, null))
                }
                2 -> { // 사용자 태그
                    chip.setChipStrokeColorResource(R.color.material_grey500)
                    chip.setTextColor(resources.getColor(R.color.material_grey500, null))
                }
            }
        }
        return chip
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_toolbar_info, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {

            }
            R.id.share_info -> {

            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        Settings.setWindowTranslucent(requireActivity().window, false)
        super.onDestroy()
    }

}