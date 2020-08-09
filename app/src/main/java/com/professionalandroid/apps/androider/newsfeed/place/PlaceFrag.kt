package com.professionalandroid.apps.androider.newsfeed.place

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.professionalandroid.apps.androider.R
import com.professionalandroid.apps.androider.navigation.NewsFeedFragment
import com.professionalandroid.apps.androider.newsfeed.realtime.ItemImageButton
import com.professionalandroid.apps.androider.newsfeed.realtime.ItemImageButtonAdapter
import com.professionalandroid.apps.androider.newsfeed.todaypost.PostFragment
import com.professionalandroid.apps.androider.newsfeed.StorePage
import com.professionalandroid.apps.androider.newsfeed.TestPost
import com.professionalandroid.apps.androider.newsfeed.TestPostAdapter
import com.professionalandroid.apps.androider.newsfeed.place.partranking.PartRank
import com.professionalandroid.apps.androider.newsfeed.place.partranking.PartRankAdapter
import com.professionalandroid.apps.androider.newsfeed.place.partranking.PartRankView
import com.professionalandroid.apps.androider.newsfeed.place.search.PlaceSearch
import com.professionalandroid.apps.androider.util.AWSRetrofit
import com.professionalandroid.apps.androider.util.RetrofitAPI
import kotlinx.android.synthetic.main.fragment1.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class PlaceFrag():Fragment(){
    //var placeFragment=PlaceSearchFrag(newsFeedView)
    companion object{ lateinit var local_btn:Button }
    var storePage= StorePage("가게 정보")
    var partRankFragment= PartRankView(storePage)
    var storeAdapter= ItemImageButtonAdapter(makeList1())
    var partRank= PartRankAdapter(makeList2())
    var todayPost= PostFragment()
    lateinit var postList:ArrayList<TestPost>
    lateinit var postAdapter:TestPostAdapter
    var notLoading=true
    lateinit var postLayoutManager: LinearLayoutManager
    lateinit var postApi:RetrofitAPI
    lateinit var retrofit:Retrofit
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?{
        val view=inflater.inflate(R.layout.fragment1,container,false)
        local_btn =view.local_search_btn
        setLocationButton(view)
        setRecyclerView1(view)
        setRecyclerView2(view)
        setTodayPost(view)
        setAllPostsOfPlace(view)
        //setSearchButton(view)
        return view
    }
    private fun moveToFragment(newFragment:Fragment){
        requireActivity().supportFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.start_more_view,
                R.anim.left_view,
                R.anim.right_view,
                R.anim.end_more_view
            )
            .addToBackStack(null).add(R.id.layout_main_content,newFragment).hide(NewsFeedFragment.thisFragment).commit()
    }
    private fun setLocationButton(view:View){
        view.local_search_btn.setOnClickListener {
            val nextIntent= Intent(requireContext(),
                PlaceSearch::class.java)
            startActivity(nextIntent)
        }
    }
    private fun setRecyclerView1(view:View){
        view.placeList1.layoutManager=LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        view.placeList1.setHasFixedSize(true)
        storeAdapter.setItemImageClickListener(object:
            ItemImageButtonAdapter.OnItemImageClickListener {
            override fun onClick(view: View,position:Int){
                moveToFragment(storePage)
            }
        })
        view.placeList1.adapter=storeAdapter
    }
    private fun makeList1():ArrayList<ItemImageButton>{
        val list= arrayListOf<ItemImageButton>()
        list.add(ItemImageButton(R.drawable.bread, "1. 미친 막창", "육류"))
        list.add(ItemImageButton(R.drawable.bread, "2. 갬성 카페", "카페"))
        list.add(
            ItemImageButton(
                R.drawable.bread,
                "3. 닭발의 지존",
                "육류"
            )
        )
        list.add(
            ItemImageButton(
                R.drawable.bread,
                "4. 마니아",
                "육류"
            )
        )
        list.add(
            ItemImageButton(
                R.drawable.bread,
                "5. 카레온",
                "식사"
            )
        )
        list.add(
            ItemImageButton(
                R.drawable.bread,
                "6. 장미 멘숀",
                "소주"
            )
        )
        list.add(
            ItemImageButton(
                R.drawable.bread,
                "7. 해쉬",
                "식사"
            )
        )
        list.add(
            ItemImageButton(
                R.drawable.bread,
                "8. 광안리 초장집",
                "회"
            )
        )
        list.add(
            ItemImageButton(
                R.drawable.bread,
                "9. 엽기 떡볶이",
                "분식"
            )
        )
        list.add(
            ItemImageButton(
                R.drawable.bread,
                "10. 아웃닭",
                "호프"
            )
        )
        return list
    }
    private fun setRecyclerView2(view:View){
        view.part_rank_list.layoutManager=LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        view.part_rank_list.setHasFixedSize(true)
        partRank.setPartLankClickListener(object : PartRankAdapter.OnPartRankClickListener{
            override fun onClick(view:View,position:Int){
                moveToFragment(partRankFragment)
            }
        })
        view.part_rank_list.adapter=partRank
    }
    private fun makeList2():ArrayList<PartRank>{
        val list= arrayListOf<PartRank>()
        for(i in 1..12) list.add(
            PartRank(
                R.drawable.ic_launcher_background,
                "카테고리",
                "순위",
                "가게이름"
            )
        )
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
        view.today_post_img.setOnClickListener {
            moveToFragment(todayPost)
        }
    }
    private fun setTodayPostContentsListener(view:View){
        view.today_post_contents.setOnClickListener {
            moveToFragment(todayPost)
        }
    }
    private fun setTodayPostStoreListener(view:View){
        view.today_post_store.setOnClickListener {
            moveToFragment(storePage)
        }
    }
    private fun setTodayPostHeartListener(view:View){
        var flag=true
        view.heart.setOnClickListener {
            var stringNum=view.number_heart.text as String
            flag = if(flag) {
                view.heart.setBackgroundResource(R.drawable.selected_heart)
                view.number_heart.text=((stringNum.toInt()+1).toString())
                false
            } else {
                view.heart.setBackgroundResource(R.drawable.not_selected_heart)
                view.number_heart.text=((stringNum.toInt()-1).toString())
                true
            }
        }
    }
    private fun setCommentListener(view:View){
        view.comment.setOnClickListener {
                moveToFragment(todayPost)
        }
    }
    private fun setAllPostsOfPlace(view:View){
//        val sign = AWSRetrofit.getAPI().takePlacePost()
//        sign.enqueue(object: Callback<List<TestPost>> {
//            override fun onFailure(call: Call<List<TestPost>>, t: Throwable) {
//                Log.d("Test",t.message)
//            }
//
//            override fun onResponse(call: Call<List<TestPost>>, response: Response<List<TestPost>>) {
//                if(response.isSuccessful){
//                    Log.d("Test","성공")
//                    val list=response.body()!!
//                    postList=ArrayList()
//                    postAdapter=TestPostAdapter(postList)
//                    postLayoutManager= LinearLayoutManager(requireContext())
//                    //view.all_post.layoutManager=LinearLayoutManager(requireContext())
//                    //view.all_post.setHasFixedSize(true)
//                    //val dataAdapter=TestPostAdapter(makeDataList(list))
//                    //view.all_post.adapter=dataAdapter
//                }
//            }
//        })
        postList=ArrayList()
        postAdapter=TestPostAdapter(postList)
        postLayoutManager= LinearLayoutManager(requireContext())
        view.all_post.adapter=postAdapter
        view.all_post.layoutManager=postLayoutManager
        postApi=AWSRetrofit.getAPI()
        load(0)
        addScrollListener(view)
    }
    private fun addScrollListener(view:View){
        view.all_post.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if(notLoading && postLayoutManager.findLastCompletelyVisibleItemPosition()==postList.size-1){
                    postList.add(TestPost(0,"progess",0,1,"11"))
                    postAdapter.notifyItemInserted(postList.size-1)
                    notLoading=false
                    val call=AWSRetrofit.getAPI().takePlacePost(postList.size-1)
                    call.enqueue(object : Callback<List<TestPost>>{
                        override fun onFailure(call: Call<List<TestPost>>, t: Throwable) {
                            Log.d("Test","실패!")
                        }

                        override fun onResponse(call: Call<List<TestPost>>, response: Response<List<TestPost>> ) {
                            postList.removeAt(postList.size-1)
                            postAdapter.notifyItemRemoved(postList.size)
                            if(response.body()!!.isNotEmpty()){
                                postList.addAll(response.body()!!)
                                postAdapter.notifyDataSetChanged()
                                notLoading=true
                            }
                            else{
                                Toast.makeText(requireContext(),"End of data reached",Toast.LENGTH_SHORT).show()
                            }
                        }
                    })
                }
            }
        })
    }
    private fun load(i:Int){
        val call=postApi.takePlacePost(i)
        call.enqueue(object : Callback<List<TestPost>>{
            override fun onFailure(call: Call<List<TestPost>>, t: Throwable) {
                Log.d("Test","연결 실패!!!")
            }

            override fun onResponse(call: Call<List<TestPost>>, response: Response<List<TestPost>>) {
                if(response.isSuccessful){
                    postList.addAll(response.body()!!)
                    postAdapter.notifyDataSetChanged()
                }
            }
        })
    }
    fun makeDataList(dataList:List<TestPost>):ArrayList<TestPost>{
        var list= arrayListOf<TestPost>()
        for(i in dataList.indices){
            list.add(TestPost(i,dataList[i].content,dataList[i].likeCount,dataList[i].type,dataList[i].timestamp))
        }
        return list
    }
//    private fun setSearchButton(view: View){
//        view.place_search_button.setOnClickListener {
//            //requireActivity().supportFragmentManager.beginTransaction().add(R.id.main_frame,fragment).hide(this).commit()
//            //이게 뭘 의미하는지 모르겟음.
//            requireActivity().supportFragmentManager.beginTransaction().addToBackStack(null)
//                .add(R.id.main_frame,placeFragment).hide(this).commit()
//            /*add 의 첫 번째 인자는 최종적으로 부모 프레임 아웃을 가져와야 함. 예를 들어 텝 레이아웃을 가리기 위해서 텝 레이아웃을 가지고 있는 xml 파일 보다
//            한 단계 위인 부모 xml 에서 프레임 레이아웃을 가져와야한다는 의미.
//            hide 의 인자로는 위에서 사용했던 부모 xml 과 함께 연동하는 클래스의 자식을 가져와야함.*/
//            requireActivity().navigation_main_bottom.visibility=View.GONE//네비게이션 바를 잠시 없애는 것.(공간 차지 o)
//            //View.INVISIBLE - View 를 감춤(공간 차지 x) View.VISIBLE - View 를 보여줌(공간 차지 o)
//        }
//    }
//    override fun onPause() {
//        Log.d("test6666666","PlaceonPause")
//        if(flag){//서치뷰가 켜져있는 상태.
//            requireActivity().supportFragmentManager.beginTransaction().remove(fragment).commit()
//            flag=false
//        }
//        super.onPause()
//    }
//
//    override fun onStop() {
//        Log.d("test6666666","PlaceonStop")
//        super.onStop()
//    }
//
//    override fun onDetach() {
//        Log.d("test6666666","PlaceonDetach")
//        super.onDetach()
//    }
}

