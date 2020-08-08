package com.professionalandroid.apps.androider.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.sql.Timestamp

@Parcelize
data class PostDTO(
    val id: Int,
    val author: Int,
    val content: String,
    val like: Int,
    val type: Int,
    val timestamp: Timestamp
) : Parcelable {
    companion object {
        const val STORE = 0
        const val ITEM = 1
    }
}