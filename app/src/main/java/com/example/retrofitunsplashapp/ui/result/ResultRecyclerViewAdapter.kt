package com.example.retrofitunsplashapp.ui.result

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofitunsplashapp.R
import com.example.retrofitunsplashapp.databinding.LayoutItemBinding
import com.example.retrofitunsplashapp.model.Photo

class ResultRecyclerViewAdapter(photoList : ArrayList<Photo>) : RecyclerView.Adapter<ResultRecyclerViewHolder>() {
    private var photoList = photoList
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultRecyclerViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = LayoutItemBinding.inflate(inflater,parent,false)
        return ResultRecyclerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ResultRecyclerViewHolder, position: Int) {
        holder.bind(photoList[position])
    }

    override fun getItemCount(): Int {
        return photoList.size
    }
    fun updateList(photoList : ArrayList<Photo>){
        this.photoList = photoList
        this.notifyDataSetChanged()
    }
}