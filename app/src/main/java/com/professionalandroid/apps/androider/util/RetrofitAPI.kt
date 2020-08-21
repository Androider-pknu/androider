package com.professionalandroid.apps.androider.util

import android.location.Location
import com.professionalandroid.apps.androider.model.AddressModel
import com.professionalandroid.apps.androider.model.ErrMsg
import com.professionalandroid.apps.androider.model.MemberDTO
import com.professionalandroid.apps.androider.model.StoreDTO
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

    /* 학진이가 추가한 부분 */
    //@GET("hakjin/store.php")
    @GET("hakjin/recommendStore.php")
    fun getStore(
        @Query("category")category: String,
        @Query("radius")radius:Double,
        @Query("latitude")latitude:Double,
        @Query("longitude")longitude:Double
    ): Call<List<StoreDTO>>

    @GET("hakjin/store.php")
    fun getStoreList(
        @Query("category")category: String,
        @Query("radius")radius:Double,
        @Query("latitude")latitude:Double,
        @Query("longitude")longitude:Double
    ): Call<List<StoreDTO>>

    @GET("hakjin/search_click_getStoreList.php")
    fun getStoreList(
        @Query("radius")radius:Double,
        @Query("latitude")latitude:Double,
        @Query("longitude")longitude:Double
    ): Call<List<StoreDTO>>

    @GET("hakjin/search_click_getStoreInfo.php")
    fun getStoreInfo(
        @Query("radius")radius:Double,
        @Query("latitude")latitude:Double,
        @Query("longitude")longitude:Double
    ): Call<List<StoreDTO>>

    @GET("hakjin/getLocalUser.php")
    fun getUserInfo(
        @Query("location")location: String
    ): Call<List<MemberDTO>>
    /* 학진이가 추가한 부분 */
}