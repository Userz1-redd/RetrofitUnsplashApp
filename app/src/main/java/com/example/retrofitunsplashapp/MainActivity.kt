package com.example.retrofitunsplashapp


import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.retrofitunsplashapp.Constants.RESPONSE_STATE
import com.example.retrofitunsplashapp.retrofit.RetrofitManager
import com.example.retrofitunsplashapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val TAG = "로그"
    private var searchType : String = "photoSearch"
    lateinit var mBinding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        //라디오 그룹 가져오기
        mBinding.searchRadioGroup.setOnCheckedChangeListener{_,id ->
            when(id){
                R.id.search_photo_rd ->{
                    Log.d(TAG, "사진검색 클릭")
                    mBinding.searchTextLayout.hint = "사진검색"
                    this.searchType = "photoSearch"
                }
                R.id.search_username_rd ->{
                    Log.d(TAG, "사용자검색 클릭")
                    mBinding.searchTextLayout.hint = "사용자검색"
                    this.searchType = "usernameSearch"
                }
            }

        }
        mBinding.searchBtn.setOnClickListener {
            RetrofitManager.instance.searchPhotos(searchTerm = mBinding.searchText.text.toString(),completion = {
                    responseState, responseBody ->
                when(responseState){
                    RESPONSE_STATE.OKAY ->{
                        Log.d(TAG, "API 호출 성공 : ${responseBody}")

                    }
                    RESPONSE_STATE.FAIL ->{
                        Toast.makeText(this,"api 호출 에러입니다",Toast.LENGTH_SHORT).show()
                    }

                }
            })
        }
    }
}