package com.professionalandroid.apps.androider.newsfeed.place.search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.SearchView
import com.professionalandroid.apps.androider.R
import kotlinx.android.synthetic.main.activity_search_view1.*

class PlaceSearch : AppCompatActivity() {
    companion object{
        lateinit var quitBtn: Button
    }
    val searchListFragment=
        SearchListFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_view1)
        quitBtn =quit
        overridePendingTransition(
            R.anim.start_search_view,
            R.anim.stay_search_view
        )//첫번째 인자는 새로 나타나는 화면이 취하는 애니메이션, 두번째 인자는 지금 화면이 취하는 애니메이션.
        local_search.isIconified=false//SearchView 키보드 띄우기.
        quit.setOnClickListener {
            finish()
            overridePendingTransition(
                R.anim.stay_search_view,
                R.anim.end_search_view
            )
        }
        local_search.setIconifiedByDefault(false) //remove delete button in SearchView
        setOnQueryTextChange()
    }
    private fun setOnQueryTextChange(){
        var queryFlag=false
        local_search.setOnQueryTextListener(object:SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {//검색버튼을 눌렀을 때.
                local_search.clearFocus()
                if(p0!=null) SearchListFragment.hintOfTest =p0
                supportFragmentManager.beginTransaction().replace(R.id.local_search_frame, searchListFragment).commit()
                searchListFragment.hintList.clear()
                searchListFragment.setRecyclerView()
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                if(p0!=null) SearchListFragment.hintOfTest =p0
                supportFragmentManager.beginTransaction().replace(R.id.local_search_frame, searchListFragment).commit()
                if(queryFlag){
                    searchListFragment.hintList.clear()
                    searchListFragment.setRecyclerView()
                }
                if(local_search.query.isEmpty()) {
                    local_search_text.text="주변 지역"
                    searchListFragment.hintList.clear()
                }
                else local_search_text.text="조회된 지역"
                queryFlag=true
                return true
            }
        })
    }
}
