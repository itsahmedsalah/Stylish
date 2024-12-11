package com.example.stylish.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.stylish.databinding.ImageItemBinding

class ImageAdapter(val list: List<String>) : RecyclerView.Adapter<ImageAdapter.Holder>() {


    inner class Holder(val binding: ImageItemBinding) : ViewHolder(binding.root) {

        fun setBind(image: String) {
            Glide.with(binding.root).load(image).into(binding.imageView)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding =
            ImageItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.setBind(list[position])
    }
}