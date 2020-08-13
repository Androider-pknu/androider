package com.professionalandroid.apps.androider.navigation.addpost.addressing

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.professionalandroid.apps.androider.R
import com.professionalandroid.apps.androider.util.SEARCH_ADDRESS_REQUEST

class ChangeAddressDialog : DialogFragment() {


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setItems(R.array.change_address_method) { _, which ->
                when (which) {
                    0 -> {
                        val intent = Intent(context, SearchAddressActivity::class.java)
                        intent.putExtra("changeAddress", true)
                        activity?.startActivityForResult(intent, SEARCH_ADDRESS_REQUEST)
                    }
                    1 -> {
                        val intent = Intent(context, ChooseBuildingActivity::class.java)
                        intent.putExtra("changeAddress", true)
                        intent.putExtra("latLng", ChangeAddressActivity.latLng)
                        activity?.startActivityForResult(intent, SEARCH_ADDRESS_REQUEST)
                    }
                }
            }
            builder.create()
        } ?: throw  IllegalStateException("Activity cannot be null")
    }
}