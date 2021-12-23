package com.example.retrofitunsplashapp.ui.result

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.retrofitunsplashapp.R
import com.example.retrofitunsplashapp.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {
    lateinit var mBinding : ActivityResultBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_result)
    }
}