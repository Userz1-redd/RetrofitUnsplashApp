package com.example.retrofitunsplashapp.retrofit

import android.util.Log

import com.example.retrofitunsplashapp.Constants.API
import com.example.retrofitunsplashapp.Constants.RESPONSE_STATE
import com.example.retrofitunsplashapp.retrofit.IRetrofit
import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.Response

class RetrofitManager {

    companion object{
        val instance = RetrofitManager()
        private const val TAG = "RetrofitManager"
    }
    //레트로핏 인터페이스 가져오기
    private val iRetrofit : IRetrofit? = RetrofitClient.getClient(API.BASE_URL)?.create(IRetrofit::class.java)

    //사진검색 api 호출
    fun searchPhotos(searchTerm: String?, completion: (RESPONSE_STATE, String) -> Unit){
        val term = searchTerm.let{
            it
        }?:""
        val call = iRetrofit?.searchPhotos(searchTerm = term).let{
            it
        }?: return
        call.enqueue(object : retrofit2.Callback<JsonElement>{
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                Log.d(TAG, "onResponse: called /t:${response.body()}")
                when(response.code()){
                    200->{
                        response.body()?.let {
                            val body = it.asJsonObject
                            val results = body.getAsJsonArray("results")
                            val total = body.get("total").asInt
                            Log.d(TAG, "onResponse: total : $total")
                            results.forEach{
                                resultItem ->
                                val resultItemObject = results.asJsonObject
                                val user = resultItemObject.get("user").asJsonObject
                            }
                        }
                    }
                }
            }

            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                Log.d(TAG, "onFailure: called")
                completion(RESPONSE_STATE.FAIL,t.toString())
            }
        })

    }
}