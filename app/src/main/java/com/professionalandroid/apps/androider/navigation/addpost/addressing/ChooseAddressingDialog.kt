package com.professionalandroid.apps.androider.navigation.addpost.addressing

import android.Manifest
import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.location.Location
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.fragment.app.DialogFragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.professionalandroid.apps.androider.R

class ChooseAddressingDialog : DialogFragment() {
    lateinit var flc: FusedLocationProviderClient
    lateinit var location: Location

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        flc = LocationServices.getFusedLocationProviderClient(context!!)
        val permission = ActivityCompat.checkSelfPermission(
            context!!,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        if (permission == PERMISSION_GRANTED) {
            flc.lastLocation.addOnSuccessListener {
                location = it
            }
        }

        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setItems(
                R.array.address_choosing_method
            ) { _, which ->
                when (which) {
                    0 -> {
                        startActivity(Intent(context, SearchAddressActivity::class.java))
                    }
                    1 -> {
                        val intent = Intent(context, ChooseBuildingActivity::class.java)
                        intent.putExtra("latLng", LatLng(location.latitude, location.longitude))
                        startActivity(intent)
                    }
                    2 -> {
                    }
                }
            }
            builder.create()
        } ?: throw  IllegalStateException("Activity cannot be null")
    }
}