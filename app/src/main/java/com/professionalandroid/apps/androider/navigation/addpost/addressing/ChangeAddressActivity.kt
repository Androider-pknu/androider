package com.professionalandroid.apps.androider.navigation.addpost.addressing

import android.app.Activity
import android.content.Intent
import android.location.Geocoder
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.professionalandroid.apps.androider.R
import com.professionalandroid.apps.androider.util.SEARCH_ADDRESS_REQUEST
import kotlinx.android.synthetic.main.activity_changeaddress.*
import java.util.*

class ChangeAddressActivity : AppCompatActivity(), OnMapReadyCallback {

    companion object {
        lateinit var latLng: LatLng
    }
    private lateinit var geocoder: Geocoder
    var map: GoogleMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_changeaddress)

        val address = intent.getStringExtra("address")
        textview_changeaddress_address.text = address

        val building = intent.getStringExtra("building")
        textinput_changeaddress_buildingname.setText(building)

        geocoder = Geocoder(this, Locale.getDefault())
        val addressList = geocoder.getFromLocationName(intent.getStringExtra("address"), 10)
        Companion.latLng = LatLng(addressList[0].latitude, addressList[0].longitude)

        val mapFragment = SupportMapFragment()
        mapFragment.getMapAsync(this)
        supportFragmentManager.beginTransaction()
            .replace(R.id.layout_changeaddress_mapcontainer, mapFragment).commit()

        layout_changeaddress_address.setOnClickListener {
            val dialog = ChangeAddressDialog()
            dialog.show(supportFragmentManager, "changeAddressDialog")
        }

        btn_changeaddress_cancel.setOnClickListener {
            onBackPressed()
        }

        btn_changeaddress_complete.setOnClickListener {
            val resultIntent = Intent()
            resultIntent.putExtra("address", textview_changeaddress_address.text.toString())
            resultIntent.putExtra("building", textinput_changeaddress_buildingname.text.toString())
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }

    }

    override fun onMapReady(googleMap: GoogleMap) {
        with(googleMap) {
            val camera = CameraUpdateFactory.newLatLngZoom(Companion.latLng, 18F)
            moveCamera(camera)

            addMarker(
                MarkerOptions()
                    .position(Companion.latLng)
            )
            uiSettings.isScrollGesturesEnabled = false
        }
        map = googleMap
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == SEARCH_ADDRESS_REQUEST) {
            when (resultCode) {
                Activity.RESULT_OK -> {
                    val address = data?.getStringExtra("address")
                    textview_changeaddress_address.text = address

                    val location = geocoder.getFromLocationName(address, 1)[0]
                    Companion.latLng = LatLng(location.latitude, location.longitude)

                    val camera = CameraUpdateFactory.newLatLngZoom(Companion.latLng, 18F)
                    map?.moveCamera(camera)

                    map?.addMarker(
                        MarkerOptions()
                            .position(Companion.latLng)
                    )
                }
            }
        }
    }
}
