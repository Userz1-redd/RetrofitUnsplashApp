package com.example.retrofitunsplashapp.ui.result

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.retrofitunsplashapp.App
import com.example.retrofitunsplashapp.Constants.RESPONSE_STATE
import com.example.retrofitunsplashapp.Constants.TAG
import com.example.retrofitunsplashapp.model.Photo
import com.example.retrofitunsplashapp.retrofit.RetrofitManager

class ResultViewModel : ViewModel() {
    private var _photoList = MutableLiveData<ArrayList<Photo>>()
    val photoList : LiveData<ArrayList<Photo>>
        get() = this._photoList



    fun searchPhoto(keyword : String, completion : (ArrayList<Photo>?) -> Unit){
        RetrofitManager.instance.searchPhotos(searchTerm = keyword,completion = {
                responseState, responseBody ->
            when(responseState){
                RESPONSE_STATE.OKAY ->{
                    Log.d(TAG, "API 호출 성공 : ${responseBody?.size}")
                    completion(responseBody)
                }
                RESPONSE_STATE.FAIL ->{
                    Toast.makeText(App.instance,"api 호출 에러입니다", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}