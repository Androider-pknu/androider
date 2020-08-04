package com.professionalandroid.apps.androider.navigation.addpost.addressing

import android.app.Activity
import android.content.Intent
import android.location.Geocoder
import android.os.Bundle
import android.widget.ImageView
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
    private lateinit var geocoder: Geocoder
    private lateinit var latLng: LatLng
    var map: GoogleMap? = null
    var marker: Marker? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choosebuilding)

        geocoder = Geocoder(this, Locale.getDefault())
        latLng = if (intent.hasExtra("address")) {
            val addressList = geocoder.getFromLocationName(intent.getStringExtra("address"), 10)
            LatLng(addressList[0].latitude, addressList[0].longitude)
        } else {
            intent.getParcelableExtra("latLng")
        }
        val mapFragment = SupportMapFragment()
        mapFragment.getMapAsync(this)
        supportFragmentManager.beginTransaction()
            .replace(R.id.layout_choosebuilding_mapcontainer, mapFragment).commit()

        btn_choosebuilding_back.setOnClickListener {
            onBackPressed()
        }

        if(intent.getBooleanExtra("changeAddress", false)){
            btn_choosebuilding_next.text = "확인"
            btn_choosebuilding_next.setOnClickListener {
                val intent = Intent()
                intent.putExtra("address", textview_choosebuilding_detailinfo.text.toString())
                setResult(Activity.RESULT_OK, intent)
                finish()
            }
        } else {
            btn_choosebuilding_next.setOnClickListener {
                val intent = Intent(this, AddStoreActivity::class.java)
                intent.putExtra("address", textview_choosebuilding_detailinfo.text.toString())
                startActivity(intent)
            }
        }

        radiogroup_choosebuilding.setOnCheckedChangeListener { _, checkedId ->
            addressingMethodChanged(checkedId)
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        with(googleMap) {
            val camera = CameraUpdateFactory.newLatLngZoom(latLng, 16F)
            moveCamera(camera)
            map = googleMap
            resetMapListener(R.id.btn_choosebuilding_building)
        }
        radiogroup_choosebuilding.check(R.id.btn_choosebuilding_building)
    }

    private fun addressingMethodChanged(checkedId: Int) {
        changeRadioBtnColor(checkedId)
        textview_choosebuilding_detailinfo.visibility = TextView.INVISIBLE
        marker?.remove()
        resetMapListener(checkedId)
    }

    private fun changeRadioBtnColor(id: Int) {
        val white = ContextCompat.getColor(applicationContext, R.color.white)
        val blue = ContextCompat.getColor(applicationContext, R.color.blue_default)

        when (id) {
            R.id.btn_choosebuilding_building -> {
                btn_choosebuilding_building.setTextColor(white)
                btn_choosebuilding_location.setTextColor(blue)
                imageview_choosebuilding_locationpoint.visibility = ImageView.INVISIBLE
            }
            R.id.btn_choosebuilding_location -> {
                btn_choosebuilding_building.setTextColor(blue)
                btn_choosebuilding_location.setTextColor(white)
                imageview_choosebuilding_locationpoint.visibility = ImageView.VISIBLE
            }
        }
    }

    private fun resetMapListener(checkedId: Int) {
        var address: String
        val gray = ContextCompat.getColor(applicationContext, R.color.gray)
        val blue = ContextCompat.getColor(applicationContext, R.color.blue_default)

        when (checkedId) {
            R.id.btn_choosebuilding_building -> map?.apply {
                setOnCameraIdleListener { }

                btn_choosebuilding_next.isEnabled = false
                btn_choosebuilding_next.setTextColor(gray)

                setOnMapClickListener {
                    address = addressOf(it)
                    marker?.remove()
                    marker = addMarker(MarkerOptions().position(it))

                    textview_choosebuilding_detailinfo.visibility = TextView.VISIBLE
                    textview_choosebuilding_detailinfo.text = address

                    btn_choosebuilding_next.isEnabled = true
                    btn_choosebuilding_next.setTextColor(blue)
                }
            }
            R.id.btn_choosebuilding_location -> map?.apply {
                setOnMapClickListener { }

                textview_choosebuilding_detailinfo.visibility = TextView.VISIBLE
                address = addressOf(cameraPosition.target)
                textview_choosebuilding_detailinfo.text = address

                btn_choosebuilding_next.isEnabled = true
                btn_choosebuilding_next.setTextColor(blue)

                setOnCameraIdleListener {
                    address = addressOf(cameraPosition.target)
                    textview_choosebuilding_detailinfo.text = address
                }
            }
        }
    }

    private fun addressOf(location: LatLng): String =
        geocoder
            .getFromLocation(location.latitude, location.longitude, 10)[0]
            .getAddressLine(0)

}
