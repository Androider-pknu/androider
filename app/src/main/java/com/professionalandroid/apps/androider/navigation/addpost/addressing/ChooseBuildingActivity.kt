package com.professionalandroid.apps.androider.navigation.addpost.addressing

import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.professionalandroid.apps.androider.R
import com.professionalandroid.apps.androider.navigation.addpost.insertdata.AddStoreActivity
import kotlinx.android.synthetic.main.activity_choosebuilding.*
import java.util.*


class ChooseBuildingActivity : AppCompatActivity(), OnMapReadyCallback {
    lateinit var geocoder: Geocoder
    lateinit var addressResult: MutableList<Address>
    lateinit var latLng: LatLng
    var map: GoogleMap? = null
    var marker: Marker? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choosebuilding)

        val mapFragment = SupportMapFragment()
        mapFragment.getMapAsync(this)

        geocoder = Geocoder(this, Locale.getDefault())
        if (intent.hasExtra("address"))
            addressResult = geocoder.getFromLocationName(intent.getStringExtra("address"), 10)

        btn_choosebuilding_next.setOnClickListener {
            startActivity(Intent(this, AddStoreActivity::class.java))
        }

        btn_choosebuilding_back.setOnClickListener {
            onBackPressed()
        }

        radiogroup_choosebuilding.setOnCheckedChangeListener { _, checkedId ->
            changeRadioBtnColor(checkedId)
            textview_choosebuilding_detailinfo.visibility = TextView.INVISIBLE
            marker?.remove()
            supportFragmentManager.beginTransaction()
                .replace(R.id.layout_choosebuilding_mapcontainer, mapFragment).commit()
            resetMapListener(checkedId)
        }

        radiogroup_choosebuilding.check(R.id.btn_choosebuilding_building)
    }

    private fun resetMapListener(checkedId: Int) {
        var address : String
        when (checkedId) {
            R.id.btn_choosebuilding_building -> map?.apply {
                setOnCameraIdleListener { }
                setOnMapClickListener {
                    address = geocoder.getFromLocation(
                        it.latitude,
                        it.longitude,
                        10
                    )[0].getAddressLine(0)
                    marker?.remove()
                    marker = addMarker(
                        MarkerOptions().position(it)
                    )
                    textview_choosebuilding_detailinfo.visibility = TextView.VISIBLE
                    textview_choosebuilding_detailinfo.text = address
                }
            }
            R.id.btn_choosebuilding_location -> map?.apply {
                textview_choosebuilding_detailinfo.visibility = TextView.VISIBLE
                setOnMapClickListener { }
                setOnCameraIdleListener {
                    address = geocoder.getFromLocation(
                        cameraPosition.target.latitude,
                        cameraPosition.target.longitude,
                        10
                    )[0].getAddressLine(0)
                    textview_choosebuilding_detailinfo.text = address
                }
            }
        }
    }

    private fun changeRadioBtnColor(id: Int) {
        fun changeColor(color1: Int, color2: Int) {
            btn_choosebuilding_building.setTextColor(
                ContextCompat.getColor(
                    applicationContext,
                    color1
                )
            )
            btn_choosebuilding_location.setTextColor(
                ContextCompat.getColor(
                    applicationContext,
                    color2
                )
            )
        }
        when (id) {
            R.id.btn_choosebuilding_building -> {
                changeColor(R.color.white, R.color.blue_default)
            }
            R.id.btn_choosebuilding_location -> {
                changeColor(R.color.blue_default, R.color.white)
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        with(googleMap) {
            latLng = if (::addressResult.isInitialized)
                LatLng(addressResult[0].latitude, addressResult[0].longitude)
            else
                intent.getParcelableExtra("latLng")
            val camera = CameraUpdateFactory.newLatLngZoom(latLng, 16F)
            moveCamera(camera)
            map = googleMap
            resetMapListener(R.id.btn_choosebuilding_building)
        }
    }
}
