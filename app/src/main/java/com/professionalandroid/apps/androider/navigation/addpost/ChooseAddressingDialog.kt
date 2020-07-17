package com.professionalandroid.apps.androider.navigation.addpost

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.professionalandroid.apps.androider.R

class ChooseAddressingDialog : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setItems(R.array.address_choosing_method,
                DialogInterface.OnClickListener { _, which ->
                    when (which) {
                        0 -> {
                            startActivity(Intent(context, SearchAddressActivity::class.java))
                        }
                        1 -> {
                        }
                        2 -> {
                        }
                    }
                })
            builder.create()
        } ?: throw  IllegalStateException("Activity cannot be null")
    }
}