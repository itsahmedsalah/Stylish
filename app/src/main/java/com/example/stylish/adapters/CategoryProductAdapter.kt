package com.example.stylish.adapters

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.stylish.R
import com.example.stylish.databinding.CategoryProductItemBinding
import com.example.stylish.databinding.ProductItemBinding
import com.example.stylish.local.MyDataBase
import com.example.stylish.models.ProductModel
import com.example.stylish.utils.MySharedPreference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CategoryProductAdapter(val productsList: List<ProductModel>) :
    RecyclerView.Adapter<CategoryProductAdapter.Holder>() {


    private var onItemClick: OnItemClick? = null

    inner class Holder(val binding: CategoryProductItemBinding) : ViewHolder(binding.root) {

        private var fav = false

        init {

            binding.root.setOnClickListener {
                onItemClick?.onClick(productsList[layoutPosition])
            }
            binding.addToWishList.setOnClickListener {
                fav = !fav
                if (fav) {
                    binding.addToWishList.setImageResource(R.drawable.fav)
                    MySharedPreference.addToFavoriteList(productsList[layoutPosition].code)
                    GlobalScope.launch(Dispatchers.IO) {
                        MyDataBase.favDataBase?.myDao()?.insertProduct(productsList[layoutPosition])
                    }
                } else {
                    binding.addToWishList.setImageResource(R.drawable.unfav)
                    MySharedPreference.removeFromFavoriteList(productsList[layoutPosition].code)
                    GlobalScope.launch(Dispatchers.IO) {
                        MyDataBase.favDataBase?.myDao()?.deleteProduct(productsList[layoutPosition])
                    }
                }
            }

        }

        private fun checkFavorite(code: String?) {
            if (MySharedPreference.getFavoriteList()!!.contains(code!!)) {
                binding.addToWishList.setImageResource(R.drawable.fav)
            }

        }

        fun setBind(product: ProductModel) {
            Glide.with(binding.root).load(product.productImage!![0]).into(binding.productImg)
            binding.tvProductName.text = product.productName
            binding.tvProductDis.text = product.productDescription
            binding.tvProductOldPrice.text = "${product.productPrice} USD"
            binding.tvProductOldPrice.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG

            val newPrice =
                product.productPrice!! - ((product.productPrice * product.productDiscount!!) / 100)

            binding.tvProductNewPrice.text = "${newPrice} USD"
            binding.tvProductOff.text = "${product.productDiscount}%OFF"
            checkFavorite(productsList[layoutPosition].code)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {

        val binding =
            CategoryProductItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun getItemCount(): Int {
        return productsList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.setBind(productsList[position])
    }

    interface OnItemClick {
        fun onClick(product: ProductModel)
    }

    fun setOnClickListener(onItemClick: OnItemClick) {
        this.onItemClick = onItemClick
    }


}