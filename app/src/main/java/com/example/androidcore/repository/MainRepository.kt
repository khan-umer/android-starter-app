package com.example.androidcore.repository

import com.example.androidcore.data.newtwork.api.ApiHelper
import com.example.androidcore.data.models.ProductList
import retrofit2.Response
import javax.inject.Inject

class MainRepository @Inject constructor(private val apiHelper: ApiHelper) {

    suspend fun getProductList():Response<ProductList> {
        return apiHelper.getProductList()
    }
}