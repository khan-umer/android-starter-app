package com.example.androidcore.data.newtwork.api

import com.example.androidcore.data.models.ProductList
import retrofit2.Response
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(private val apiService: ApiService) : ApiHelper {


    override suspend fun userLogin(username: String, password: String): Response<String> {
        return apiService.userLogin(username = username, password = password)
    }

    override suspend fun getProductList(): Response<ProductList> {
        return apiService.getProductList()
    }

}