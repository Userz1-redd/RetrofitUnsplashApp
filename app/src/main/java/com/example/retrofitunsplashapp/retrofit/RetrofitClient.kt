package com.example.retrofitunsplashapp.retrofit


import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import com.example.retrofitunsplashapp.App
import com.example.retrofitunsplashapp.Constants.API
import com.example.retrofitunsplashapp.Constants.isJsonArray
import com.example.retrofitunsplashapp.Constants.isJsonObject

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

// 싱글턴
object RetrofitClient {
    private const val TAG = "RetrofitClient"
    //레트로핏 클라이언트 선언
    private var retrofitClient : Retrofit? = null


    //레트로핏 클라이언트 가져오기
    fun getClient(baseUrl: String): Retrofit?{
        Log.d(TAG, "getClient: called")

        //okhttp 인스턴스 생성
        val client = OkHttpClient.Builder()
        //로그 인터셉터
        val loggingInterceptor = HttpLoggingInterceptor(object: HttpLoggingInterceptor.Logger{
            override fun log(message: String) {
                Log.d(TAG, "log: message: $message")
                when{
                    message.isJsonObject() ->
                        Log.d(TAG, JSONObject(message).toString(4))
                    message.isJsonArray() ->
                        Log.d(TAG, JSONObject(message).toString(4))
                    else ->{
                        try{
                            Log.d(TAG, JSONObject(message).toString(4))
                        }catch (e: Exception){
                            Log.d(TAG, message)
                        }
                    }
                }
            }

        })
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        client.addInterceptor(loggingInterceptor)

        //기본 파라메터 추가
        val baseParameterInterceptor : Interceptor = (object : Interceptor{
            override fun intercept(chain: Interceptor.Chain): Response {
                Log.d(TAG, "intercept: called")
                val originalRequest = chain.request()

                //쿼리 파라메터 추가하기
                val addedUrl = originalRequest.url.newBuilder().addQueryParameter("client_id", API.CLIENT_ID).build()
                val finalRequest = originalRequest.newBuilder()
                    .url(addedUrl).method(originalRequest.method,originalRequest.body)
                    .build()

                val response = chain.proceed(finalRequest)
                if(response.code!=200){
                    Handler(Looper.getMainLooper()).post{
                        Toast.makeText(App.instance,"${response.code} Error",Toast.LENGTH_SHORT).show()
                    }
                }
                return response
            }

        })
        client.addInterceptor(baseParameterInterceptor)
        //커넥션 타임아웃
        client.connectTimeout(10, TimeUnit.SECONDS)


        if(retrofitClient==null){

            //레트로핏 빌더로 인스턴스 생성
            retrofitClient = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                // 위에서 설정한 클라이언트로 레트로핏 클라이언트를 설정
                .client(client.build())
                .build()
        }
        return retrofitClient
    }
}