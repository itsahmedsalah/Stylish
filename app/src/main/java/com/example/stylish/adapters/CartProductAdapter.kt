package com.example.stylish.adapters

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.stylish.databinding.CheckOutItemBinding
import com.example.stylish.models.ProductModel

class CartProductAdapter(private val productList: List<ProductModel>) :
    RecyclerView.Adapter<CartProductAdapter.Holder>() {

    private var onItemClick: OnItemClick? = null

    inner class Holder(val binding: CheckOutItemBinding) : RecyclerView.ViewHolder(binding.root) {


        init {
            binding.btnRemoveProduct.setOnClickListener {
                onItemClick?.onDeleteClick(productList[layoutPosition])
            }
        }

        fun setBind(productModel: ProductModel) {
            Glide.with(binding.root).load(productModel.productImage!![0]).into(binding.productImg)
            binding.tvProductName.text = productModel.productName
            binding.tvProductOff.text = "${productModel.productDiscount}%OFF"
            binding.tvProductOldPrice.text = "${productModel.productPrice} USD"
            binding.tvProductOldPrice.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG

            val newPrice =
                productModel.productPrice!! - ((productModel.productPrice * productModel.productDiscount!!) / 100)

            binding.tvProductNewPrice.text = "${newPrice} USD"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding =
            CheckOutItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.setBind(productList[position])
    }

    interface OnItemClick {
        fun onDeleteClick(product: ProductModel)
    }

    fun setOnClickListener(onItemClick: OnItemClick) {
        this.onItemClick = onItemClick
    }
}