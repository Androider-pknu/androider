package com.professionalandroid.apps.androider.navigation.addpost.addressing

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.professionalandroid.apps.androider.R

class CancelItemDialogFragment() : DialogFragment() {
    internal lateinit var listener: NoticeDialogListener

    interface NoticeDialogListener {
        fun onDialogCompleteClick()
    }

    override fun onAttach(context: Context) {
        super.onAttach(requireContext())

        listener = context as? NoticeDialogListener
            ?: throw ClassCastException((context.toString() + " must implement NoticeDialogListener")) as Throwable
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setItems(R.array.cancel_item, DialogInterface.OnClickListener { _, _ ->
                listener.onDialogCompleteClick()
                dismiss()
            })
            builder.create()
        } ?: throw  IllegalStateException("Activity cannot be null")
    }
}