package com.professionalandroid.apps.androider.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.sql.Timestamp

@Parcelize
data class PostDTO(
    val post_id: Int,
    val author_id: Int,
    val content: String,
    val id: Int,
    val like: Int,
    val type: Int,
    val timestamp: Timestamp
) : Parcelable {
    companion object {
        const val STORE = 1
        const val ITEM = 2
    }
}