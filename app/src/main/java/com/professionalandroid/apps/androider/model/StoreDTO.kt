package com.professionalandroid.apps.androider.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class StoreDTO(
    val id: Int,
    val name: String,
    val category: String,
    val address: String,
    val number: String?,
    val distance: Double,
    val latitude: Double,
    val longitude: Double
) : Parcelable