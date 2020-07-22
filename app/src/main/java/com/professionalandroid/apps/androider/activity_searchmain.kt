package com.professionalandroid.apps.androider

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.GridView
import android.widget.SearchView
import com.professionalandroid.apps.androider.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_search_main.*
import kotlinx.android.synthetic.main.fragment_search.*

class activity_searchmain : AppCompatActivity() {

    private lateinit var img: ArrayList<Int>
    private lateinit var name: ArrayList<String>
    private lateinit var location: ArrayList<String>

    var con: Context = this
    private lateinit var gv: GridView
    private lateinit var cl: CustomAdapter

    private lateinit var hotPlaceFragment: HotPlaceFragment
    private lateinit var searchLocationMenuFragment: SearchLocationMenuFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_main)
        initGridView() // gridview 초기화

        /* 서치뷰 전체영역 터치*/
        search_view.setOnClickListener {
            search_view.onActionViewExpanded()
            //search_view.isFocusable = true;
        }
        search_view.queryHint = "지역 장소 메뉴 검색"

        search_view.setOnQueryTextListener(object:
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            /*이 메소드는 실시간으로 변화는것을 볼떄 사용*/
            override fun onQueryTextChange(newText: String?): Boolean {
                //setFlag(1)
                return false
            }

            /*onQueryTextChange 는 내가 작성을하고 검색버튼을 눌렀을때 사용되는 메소드*/
            override fun onQueryTextSubmit(query: String?): Boolean {
                onFragmentChanged(0)
                return true
            }
        })

        //그리드뷰 adapter 설정
        gv = findViewById<GridView>(R.id.gridView)
        cl = CustomAdapter(img, con, name,location)
        gv.adapter=cl
    }


    private fun onFragmentChanged(index: Int): Unit{
        hotPlaceFragment = HotPlaceFragment()
        //hotPlaceFragment = fragmentManager.findFragmentById(R.id.hotPlaceFragment) as HotPlaceFragment
        searchLocationMenuFragment = SearchLocationMenuFragment()
        if(index == 0) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, searchLocationMenuFragment).addToBackStack(null).commit()

            //supportFragmentManager.beginTransaction().replace(R.id.containerFragment,SearchLocationMenuFragment()).commit()
        }
        else if(index == 1)
            //supportFragmentManager.beginTransaction().replace(R.id.containerFragment,HotPlaceFragment()).commit()
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container,hotPlaceFragment).commit()
    }

    //그리드뷰  데이터 초기화
    private fun initGridView(){
        img = ArrayList()
        name = ArrayList()
        location = ArrayList()
        for (i in 1..15){
            img.add(R.drawable.image01)
            name.add("연어덮밥")
            location.add("유니온썬 타워")
        }
    }

}












