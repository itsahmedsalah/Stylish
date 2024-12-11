package com.example.stylish.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.stylish.databinding.CategoryItemBinding
import com.example.stylish.models.CategoriesModel

class CategoryAdapter(val categoryList: List<CategoriesModel>) :
    RecyclerView.Adapter<CategoryAdapter.Holder>() {

    private var onItemClick: OnItemClick? = null

    inner class Holder(val binding: CategoryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                onItemClick?.onClickListener(categoryList[adapterPosition].title!!)
            }
        }


        fun bind(categoryItem: CategoriesModel) {
            binding.categoryTitle.text = categoryItem.title
            Glide.with(binding.root.context).load(categoryItem.imageUrl).into(binding.categoryImg)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {

        val binding =
            CategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(categoryList[position])
    }

    fun setOnClickListener(onItemClick: OnItemClick) {
        this.onItemClick = onItemClick
    }

    interface OnItemClick {
        fun onClickListener(type: String)
    }


}