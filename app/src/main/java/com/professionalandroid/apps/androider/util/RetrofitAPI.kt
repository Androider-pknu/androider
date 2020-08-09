package com.professionalandroid.apps.androider.util

import com.professionalandroid.apps.androider.model.AddressModel
import com.professionalandroid.apps.androider.model.ErrMsg
import com.professionalandroid.apps.androider.model.StoreDTO
import com.professionalandroid.apps.androider.model.TagDTO
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

    @GET("/ostest/getTags.php")
    fun getTags(
        @Query("id") storeID: Int
    ): Call<List<TagDTO>>
}