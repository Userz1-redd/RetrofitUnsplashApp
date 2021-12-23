package com.example.retrofitunsplashapp.ui.result

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.retrofitunsplashapp.Constants.TAG
import com.example.retrofitunsplashapp.R
import com.example.retrofitunsplashapp.databinding.ActivityResultBinding
import com.example.retrofitunsplashapp.model.Photo
import com.example.retrofitunsplashapp.ui.main.MainViewModel

class ResultActivity : AppCompatActivity() {
    lateinit var mBinding : ActivityResultBinding
    lateinit var viewModel : ResultViewModel
    lateinit var adapter : ResultRecyclerViewAdapter
    lateinit var photoList : ArrayList<Photo>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_result)
        viewModel = ViewModelProvider(this).get(ResultViewModel::class.java)
        getApiResult()
        settingRecyclerView()
    }
    fun settingRecyclerView(){
        mBinding.recyclerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false)
        this.adapter = ResultRecyclerViewAdapter(photoList)
        mBinding.recyclerView.adapter=  this.adapter
    }
    fun getApiResult(){
        var bundle = intent.getBundleExtra("data")
        this.photoList = bundle!!.getSerializable("data") as ArrayList<Photo>
        Log.d(TAG, "getApiResult: ${photoList.size}")
    }
}