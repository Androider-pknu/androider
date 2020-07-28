package com.professionalandroid.apps.androider.navigation.addpost.addstore.addressing

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapFragment
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.professionalandroid.apps.androider.R
import com.professionalandroid.apps.androider.navigation.addpost.addstore.input.AddStoreActivity
import kotlinx.android.synthetic.main.activity_choosebuilding.*


class ChooseBuildingActivity : AppCompatActivity(), OnMapReadyCallback {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choosebuilding)
        btn_sample.setOnClickListener {
            startActivity(Intent(this, AddStoreActivity::class.java))
        }
        btn_choosebuilding_building.setOnClickListener {
            btn_choosebuilding_building.setTextColor(ContextCompat.getColor(applicationContext, R.color.white))
            btn_choosebuilding_location.setTextColor(ContextCompat.getColor(applicationContext, R.color.blue_default))
        }
        btn_choosebuilding_location.setOnClickListener {
            btn_choosebuilding_building.setTextColor(ContextCompat.getColor(applicationContext, R.color.blue_default))
            btn_choosebuilding_location.setTextColor(ContextCompat.getColor(applicationContext, R.color.white))
        }
        btn_choosebuilding_back.setOnClickListener {
            onBackPressed()
        }

        val mapFragment : SupportMapFragment? =
            supportFragmentManager.findFragmentById(R.id.fragment_choosebuilding_mapfragment) as? SupportMapFragment
        mapFragment?.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        googleMap ?: return
        with(googleMap) {
            addMarker(MarkerOptions()
                .position(LatLng(0.0, 0.0))
                .title("Marker"))
        }
    }
}
