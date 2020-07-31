package com.professionalandroid.apps.androider

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_moreandmore.*

class MoreAndMore : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_moreandmore)
        overridePendingTransition(R.anim.start_more_view,R.anim.left_view)
        setButton()
        //setRecyclerView()
    }
//    private fun setRecyclerView(){
//        val list=makeList()
//        show_rank.layoutManager= LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
//        show_rank.setHasFixedSize(true)
//        show_rank.adapter=RankingAdapter(list)
//    }
    private fun makeList():ArrayList<ExploreRanking>{
        val list= arrayListOf<ExploreRanking>()
//        list.add(Ranking(R.drawable.test3,"1영게이","@fuck","나는 게이다!"))
//        list.add(Ranking(R.drawable.test3,"2김영민","@GAY","나는 좆게이"))
//        list.add(Ranking(R.drawable.test3,"3영게이","@fuck","나는 게이다!"))
//        list.add(Ranking(R.drawable.test3,"4김영민","@GAY","나는 좆게이"))
//        list.add(Ranking(R.drawable.test3,"5영게이","@fuck","나는 게이다!"))
//        list.add(Ranking(R.drawable.test3,"6김영민","@GAY","나는 좆게이"))
//        list.add(Ranking(R.drawable.test3,"7영게이","@fuck","나는 게이다!"))
//        list.add(Ranking(R.drawable.test3,"8김영민","@GAY","나는 좆게이"))
//        list.add(Ranking(R.drawable.test3,"9영게이","@fuck","나는 게이다!"))
//        list.add(Ranking(R.drawable.test3,"10김영민","@GAY","나는 좆게이"))
//        list.add(Ranking(R.drawable.test3,"11영게이","@fuck","나는 게이다!"))
//        list.add(Ranking(R.drawable.test3,"12김영민","@GAY","나는 좆게이"))
//        list.add(Ranking(R.drawable.test3,"13영게이","@fuck","나는 게이다!"))
//        list.add(Ranking(R.drawable.test3,"14김영민","@GAY","나는 좆게이"))
//        list.add(Ranking(R.drawable.test3,"15영게이","@fuck","나는 게이다!"))
//        list.add(Ranking(R.drawable.test3,"16김영민","@GAY","나는 좆게이"))
//        list.add(Ranking(R.drawable.test3,"17영게이","@fuck","나는 게이다!"))
//        list.add(Ranking(R.drawable.test3,"18김영민","@GAY","나는 좆게이"))
//        list.add(Ranking(R.drawable.test3,"19영게이","@fuck","나는 게이다!"))
//        list.add(Ranking(R.drawable.test3,"20김영민","@GAY","나는 좆게이"))
        return list
    }
    private fun setButton(){
        back.setOnClickListener {
            finish()
            overridePendingTransition(R.anim.right_view,R.anim.end_more_view)
        }
    }
}
