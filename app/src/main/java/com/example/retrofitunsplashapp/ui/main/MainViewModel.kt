package com.example.retrofitunsplashapp.ui.main


import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.retrofitunsplashapp.App
import com.example.retrofitunsplashapp.Constants.RESPONSE_STATE
import com.example.retrofitunsplashapp.Constants.TAG
import com.example.retrofitunsplashapp.retrofit.RetrofitManager


class MainViewModel : ViewModel() {
    private var _search_term = MutableLiveData<String>()

    val search_term : LiveData<String>
        get() = _search_term

    fun searchPhoto(keyword : String, completion:(Bundle) -> Unit){
        RetrofitManager.instance.searchPhotos(searchTerm = keyword,completion = {
                responseState, responseBody ->
            when(responseState){
                RESPONSE_STATE.OKAY ->{
                    Log.d(TAG, "API 호출 성공 : ${responseBody?.size}")
                    var bundle = Bundle()
                    bundle.putSerializable("data",responseBody)
                    completion(bundle)
                }
                RESPONSE_STATE.FAIL ->{
                    Toast.makeText(App.instance,"api 호출 에러입니다", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}