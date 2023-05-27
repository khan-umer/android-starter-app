package com.example.androidcore.data.newtwork.api

import com.example.androidcore.data.models.ProductList
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @FormUrlEncoded
    @POST("auth/login")
    suspend fun userLogin(
        @Field("username")
        username: String,

        @Field("password")
        password: String
    ): Response<String>

    @GET("products")
    suspend fun getProductList(): Response<ProductList>

    @GET("carts/user/1")
    suspend fun getUserCartList(): Response<ProductList>




}