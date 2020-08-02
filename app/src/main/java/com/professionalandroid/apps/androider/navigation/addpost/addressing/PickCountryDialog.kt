package com.professionalandroid.apps.androider.navigation.addpost.addressing

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.professionalandroid.apps.androider.R
import kotlinx.android.synthetic.main.dialog_searchaddress_countrypicker.*

class PickCountryDialog : BottomSheetDialogFragment() {
    internal lateinit var listener: NoticeDialogListener

    interface NoticeDialogListener {
        fun onDialogCompleteClick(value: String)
    }

    override fun onAttach(context: Context) {
        super.onAttach(requireContext())

        listener = context as? NoticeDialogListener
            ?: throw ClassCastException((context.toString() + " must implement NoticeDialogListener")) as Throwable
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_searchaddress_countrypicker, container, false)
    }

    override fun onStart() {
        super.onStart()

        initNumberPicker()
        btn_countrypicker_pickcountry.setOnClickListener {
            val returnValue =
                resources.getStringArray(R.array.country_list)[numberpicker_pickcountrydialog.value]
            listener.onDialogCompleteClick(returnValue)
            dismiss()
        }
    }

    private fun initNumberPicker() {
        numberpicker_pickcountrydialog.minValue = 0
        numberpicker_pickcountrydialog.maxValue = 3
        numberpicker_pickcountrydialog.displayedValues =
            resources.getStringArray(R.array.country_list)
        numberpicker_pickcountrydialog.value = 3
        numberpicker_pickcountrydialog.wrapSelectorWheel = false
    }
}