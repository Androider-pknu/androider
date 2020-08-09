package com.professionalandroid.apps.androider.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TagDTO(
    val name: String,
    val type: Int
) : Parcelable