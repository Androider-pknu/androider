package com.professionalandroid.apps.androider.util

import com.professionalandroid.apps.androider.model.AddressModel
import com.professionalandroid.apps.androider.model.ErrMsg
import com.professionalandroid.apps.androider.model.StoreDTO
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

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
}