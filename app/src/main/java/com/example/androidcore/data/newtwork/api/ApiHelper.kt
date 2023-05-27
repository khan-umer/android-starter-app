package com.example.androidcore.data.newtwork.api

import com.example.androidcore.data.models.ProductList
import retrofit2.Response

interface ApiHelper {

    suspend fun userLogin(username:String,password:String): Response<String>

    suspend fun getProductList(): Response<ProductList>


}