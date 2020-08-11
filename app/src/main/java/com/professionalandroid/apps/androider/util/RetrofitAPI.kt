package com.professionalandroid.apps.androider.util

import com.professionalandroid.apps.androider.model.AddressModel
import com.professionalandroid.apps.androider.model.ErrMsg
import com.professionalandroid.apps.androider.model.StoreDTO
import com.professionalandroid.apps.androider.newsfeed.TestPost
import retrofit2.Call
import retrofit2.http.*

interface RetrofitAPI {
    @FormUrlEncoded
    @POST("/shtest/searchAddress.php")
    fun searchAddress(
        @Field("address") text: String
    ): Call<AddressModel>

    @FormUrlEncoded
    @POST("/shtest/dbInsertTest.php")
    fun insertStore(
        @Field("name") name: String,
        @Field("category") category: String,
        @Field("address") address: String,
        @Field("number") number: String?
    ): Call<StoreDTO>

    @GET("jongyoon/post_test.php")
    fun takePlacePost(@Query("index1")start:Int,@Query("type")type:Int):Call<List<TestPost>>
//    @GET("jongyoon/post_test.php")
//    fun takePlacePost(@Query("page")page:Int,@Query("limit")limit:Int):Call<List<TestPost>>
}