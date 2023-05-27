package com.example.androidcore.data.models


import com.google.gson.annotations.SerializedName

data class UserCartListItem(
    @SerializedName("date")
    val date: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("products")
    val products: List<Product>,
    @SerializedName("userId")
    val userId: Int,
    @SerializedName("__v")
    val v: Int
)