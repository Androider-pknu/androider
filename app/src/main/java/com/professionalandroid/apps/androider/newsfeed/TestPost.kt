package com.professionalandroid.apps.androider.newsfeed

import com.google.gson.annotations.SerializedName

data class TestPost(
    @SerializedName("post_id")
    val author:Int,
    @SerializedName("content")
    val content:String,
    @SerializedName("like_count")
    val likeCount:Int,
    @SerializedName("type")
    val type:Int,
    @SerializedName("timestamp")
    val timestamp:String
)