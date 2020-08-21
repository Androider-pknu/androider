package com.professionalandroid.apps.androider.newsfeed.loaddata

import com.google.gson.annotations.SerializedName

data class TestPost(
    @SerializedName("content")
    val content:String,
    @SerializedName("like_count")
    val likeCount:Int,
    @SerializedName("type")
    val type:Int,
    @SerializedName("comment_count")
    val commentCount:Int,
    @SerializedName("timestamp")
    val timestamp:String,
    @SerializedName("post_image_url")
    val image:ArrayList<String>?,
    @SerializedName("user_id")
    val userID:String,
    @SerializedName("nickname")
    val nickName:String,
    @SerializedName("member_image_url")
    val imageOfMember:String?
)