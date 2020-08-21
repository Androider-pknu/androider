package com.professionalandroid.apps.androider.model

import android.os.Parcelable
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MemberDTO(
    val id: Int,
    val name: String,
    val image_url: String?,
    var postCount: Int,
    val postLocationList: ArrayList<LatLng>,
    var post_image_url: ArrayList<String>?
): Parcelable

