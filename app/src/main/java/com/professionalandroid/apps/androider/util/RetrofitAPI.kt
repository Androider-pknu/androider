package com.professionalandroid.apps.androider.util

import com.professionalandroid.apps.androider.model.AddressModel
import com.professionalandroid.apps.androider.model.ItemDTO
import com.professionalandroid.apps.androider.model.PostDTO
import com.professionalandroid.apps.androider.model.StoreDTO
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*
import java.sql.Timestamp
import com.professionalandroid.apps.androider.newsfeed.loaddata.TestPost
import retrofit2.http.*
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

    @Multipart
    @POST("/shtest/addPost.php")
    fun addPost(
        @Part("author_id") author_id: Int,
        @Part("content") content: String,
        @Part("id") id: Int,
        @Part("type") type: Int,
        @Part imageFiles : ArrayList<MultipartBody.Part>
    ): Call<String>
    @GET("jongyoon/post_test.php")
    fun takePlacePost(@Query("index1")start:Int,@Query("type")type:Int):Call<List<TestPost>>
}