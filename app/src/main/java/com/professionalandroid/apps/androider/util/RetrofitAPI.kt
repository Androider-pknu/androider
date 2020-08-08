package com.professionalandroid.apps.androider.util

import com.professionalandroid.apps.androider.model.AddressModel
import com.professionalandroid.apps.androider.model.ItemDTO
import com.professionalandroid.apps.androider.model.PostDTO
import com.professionalandroid.apps.androider.model.StoreDTO
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import java.sql.Timestamp

interface RetrofitAPI {
    @FormUrlEncoded
    @POST("/shtest/searchAddress.php")
    fun searchAddress(
        @Field("address") text: String
    ): Call<AddressModel>

    @FormUrlEncoded
    @POST("/shtest/addStore.php")
    fun addStore(
        @Field("name") name: String,
        @Field("category") category: String,
        @Field("address") address: String,
        @Field("number") number: String?
    ): Call<StoreDTO>

    @FormUrlEncoded
    @POST("/shtest/addItem.php")
    fun addItem(
        @Field("name") name: String,
        @Field("category") category: String
    ): Call<ItemDTO>

    @FormUrlEncoded
    @POST("/shtest/addPost.php")
    fun addPost(
        @Field("author") author: Int,
        @Field("content") content: String,
        @Field("like") like: Int,
        @Field("type") type: Int,
        @Field("timestamp") timestamp: Timestamp
    ): Call<PostDTO>
}