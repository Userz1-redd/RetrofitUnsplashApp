package com.example.retrofitunsplashapp.ui.result

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofitunsplashapp.databinding.LayoutItemBinding
import com.example.retrofitunsplashapp.model.Photo

class ResultRecyclerViewHolder(private val binding : LayoutItemBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(item : Photo){
        binding.apply{
            this.itemVar = item
            this.executePendingBindings()
        }
    }
}