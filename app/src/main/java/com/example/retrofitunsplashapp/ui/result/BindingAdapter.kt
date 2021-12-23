package com.example.retrofitunsplashapp.ui.result

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.retrofitunsplashapp.R

@BindingAdapter("thumbnail")
fun loadImage(view : ImageView, imageUrl : String){
    Glide.with(view.context)
        .load(imageUrl)
        .error(R.drawable.ic_launcher_foreground)
        .into(view)
}