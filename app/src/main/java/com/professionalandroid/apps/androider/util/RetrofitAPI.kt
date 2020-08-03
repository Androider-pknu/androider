package com.professionalandroid.apps.androider.util

import com.professionalandroid.apps.androider.model.AddressModel
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface RetrofitAPI {
    @FormUrlEncoded
    @POST("ex.php")
    fun awsPost(
        @Field("address") text: String
    ): Call<AddressModel>
}