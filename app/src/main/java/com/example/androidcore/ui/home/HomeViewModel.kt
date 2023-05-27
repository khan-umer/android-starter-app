package com.example.androidcore.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidcore.data.newtwork.NetworkResult
import com.example.androidcore.data.models.ProductList
import com.example.androidcore.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

private val TAG = "TAG_LOG::" + HomeViewModel::class.java.simpleName

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: MainRepository) : ViewModel() {

    private val _productList: MutableLiveData<NetworkResult<ProductList>> =
        MutableLiveData(NetworkResult.Loading())
    val productList: LiveData<NetworkResult<ProductList>> = _productList


    private fun getProductList() {
        viewModelScope.launch {
            val resultDeferred = async(Dispatchers.IO) { repository.getProductList() }
            val result = resultDeferred.await()
            if (result.isSuccessful) {
                result.body()?.let {
                    _productList.postValue(NetworkResult.Success(data = it))
                }
            } else {
                _productList.postValue(NetworkResult.Error(result.message()))
            }
        }
    }

    init {
        Log.d(TAG, "init: ")
        getProductList()
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "onCleared: ")
    }
}