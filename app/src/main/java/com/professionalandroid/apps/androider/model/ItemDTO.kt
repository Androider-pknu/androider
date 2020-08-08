package com.professionalandroid.apps.androider.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ItemDTO(
    val id: Int,
    val name: String,
    val category: String
) : Parcelable