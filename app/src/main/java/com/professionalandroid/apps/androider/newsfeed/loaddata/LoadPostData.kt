package com.professionalandroid.apps.androider.newsfeed.loaddata

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.View
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.professionalandroid.apps.androider.MainActivity
import com.professionalandroid.apps.androider.R
import com.professionalandroid.apps.androider.navigation.NewsFeedFragment
import com.professionalandroid.apps.androider.newsfeed.todaypost.PostFragment
import com.professionalandroid.apps.androider.util.AWSRetrofit
import com.professionalandroid.apps.androider.util.RetrofitAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoadPostData(private val recyclerView: RecyclerView, val context:Context,val activity:FragmentActivity,val type:Int){
    private var postList:ArrayList<TestPost> = ArrayList()
    private var postAdapter: TestPostAdapter =
        TestPostAdapter(postList, context)
    private var notLoading=true
    private var postLayoutManager: LinearLayoutManager = LinearLayoutManager(context)
    private var postApi:RetrofitAPI = AWSRetrofit.getAPI()
    private var endPoint=20
    private var startPoint=0;

    fun loadPlacePost(start:Int){
        val call=postApi.takePlacePost(start,type)
        call.enqueue(object : Callback<List<TestPost>>{
            override fun onFailure(call: Call<List<TestPost>>, t: Throwable) {
                Log.d("Test","연결 실패!!!")
            }

            override fun onResponse(call: Call<List<TestPost>>, response: Response<List<TestPost>>) {
                if(response.isSuccessful){
                    postList.addAll(response.body()!!)
                    postAdapter.notifyItemRangeChanged(0,endPoint)
                }
            }
        })
    }

    fun addScrollListener(){
        recyclerView.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if(notLoading && postLayoutManager.findLastCompletelyVisibleItemPosition()==postList.size-1){
//                    postList.add(TestPost(0,"nothing",0,1,"11"))
//                    postAdapter.notifyItemInserted(postList.size-1) -> 이렇게 하면 자꾸 스크롤이 튕기던데 이유를 잘 모르겠음. 그래서 일단 주석처리 해놓음.
                    notLoading=false
                    startPoint=endPoint
                    endPoint+=20
                    val handler = android.os.Handler()//너무 빨리 데이터가 로드되면 스크롤 되는 Ui 를 확인하기 어려우므로
                    handler.postDelayed({//Handler 를 사용하여 1초간 postDelayed 시킴.
                        val call= AWSRetrofit.getAPI().takePlacePost(startPoint,type)
                        call.enqueue(object : Callback<List<TestPost>> {
                            override fun onFailure(call: Call<List<TestPost>>, t: Throwable) {
                                Log.d("Test","실패!")
                            }

                            override fun onResponse(call: Call<List<TestPost>>, response: Response<List<TestPost>>) {
//                            postList.removeAt(postList.size-1)
//                            postAdapter.notifyItemRemoved(postList.size)
                                if(response.body()!!.isNotEmpty()){
                                    postList.addAll(response.body()!!)
                                    postAdapter.notifyItemRangeChanged(startPoint,20)
                                    notLoading=true
                                }
                            }
                        })},1000)
                }
            }
        })
    }
    fun setRecyclerListener(){
        postAdapter.setPostClickListener(object : TestPostAdapter.OnPostClickListener {
            override fun onClick(view: View, position: Int) {
                activity.supportFragmentManager.beginTransaction().setCustomAnimations(R.anim.start_more_view, R.anim.left_view, R.anim.right_view, R.anim.end_more_view)
                    .addToBackStack(null).add(R.id.layout_main_content,PostFragment(postList[position])).hide(NewsFeedFragment.thisFragment).commit()
            }
          })
    }
    fun getAdapter() : TestPostAdapter = postAdapter
    fun getLayoutManager() : RecyclerView.LayoutManager = postLayoutManager
}