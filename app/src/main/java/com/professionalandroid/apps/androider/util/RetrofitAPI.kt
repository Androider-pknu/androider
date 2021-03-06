package com.professionalandroid.apps.androider.util

import com.professionalandroid.apps.androider.model.AddressModel
import com.professionalandroid.apps.androider.model.ItemDTO
import com.professionalandroid.apps.androider.model.MemberDTO
import com.professionalandroid.apps.androider.model.StoreDTO
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*
import com.professionalandroid.apps.androider.newsfeed.loaddata.TestPost
import okhttp3.ResponseBody

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

    @FormUrlEncoded
    @POST("jongyoon/signUp.php")
    fun singUp(
        @Field("user_id") userId:String, @Field("password") passWord:String,
        @Field("email") email:String, @Field("nickname") nickName:String
    ): Call<Unit>

    @FormUrlEncoded
    @POST("jongyoon/signIn.php")
    fun signIn(
        @Field("email") email:String, @Field("password") password:String
    ) : Call<String>
}