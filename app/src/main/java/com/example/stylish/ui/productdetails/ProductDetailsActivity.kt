package com.example.stylish.ui.productdetails

import android.annotation.SuppressLint
import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.stylish.R
import com.example.stylish.adapters.ImageAdapter
import com.example.stylish.databinding.ActivityProductDetailsBinding
import com.example.stylish.local.MyDataBase
import com.example.stylish.models.ProductModel
import com.example.stylish.utils.MySharedPreference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

class ProductDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductDetailsBinding
    private var imageAdapter: ImageAdapter? = null
    private var fav = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val json = intent.getStringExtra("product")
        val product = Json.decodeFromString<ProductModel>(json!!)
        setBind(product)
        onClick(product)
    }


    private fun onClick(product: ProductModel) {

        binding.btnBackArrow.setOnClickListener {
            finish()
        }
        binding.addToWishList.setOnClickListener {
            fav = !fav
            if (fav) {
                binding.addToWishList.setImageResource(R.drawable.fav)
                MySharedPreference.addToFavoriteList(product.code)
                lifecycleScope.launch(Dispatchers.IO) {
                    MyDataBase.favDataBase?.myDao()?.insertProduct(product)
                }
            } else {
                binding.addToWishList.setImageResource(R.drawable.unfav)
                MySharedPreference.removeFromFavoriteList(product.code)
                lifecycleScope.launch(Dispatchers.IO) {
                    MyDataBase.favDataBase?.myDao()?.deleteProduct(product)
                }
            }
        }

        binding.btnAddToCart.setOnClickListener {

            Toast.makeText(this, "Add to Cart", Toast.LENGTH_LONG).show()
            lifecycleScope.launch(Dispatchers.IO) {
                MyDataBase.cartDataBase?.myDao()?.insertProduct(product)
            }

        }


    }


    @SuppressLint("ResourceAsColor", "SetTextI18n")
    private fun setBind(product: ProductModel) {

        imageAdapter = ImageAdapter(product.productImage!!)
        binding.imgViewPager.adapter = imageAdapter
        binding.dotsIndicator.attachTo(binding.imgViewPager)
        binding.tvProductName.text = product.productName
        binding.tvProductDis.text = product.productDescription
        binding.tvProductOldPrice.text = "${product.productPrice} USD"
        binding.tvProductOldPrice.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
        val newPrice =
            product.productPrice!! - ((product.productPrice * product.productDiscount!!) / 100)
        binding.tvProductNewPrice.text = "${newPrice} USD"
        binding.tvProductOff.text = "${product.productDiscount}%OFF"
        checkFavorite(product.code)

    }

    private fun checkFavorite(code: String?) {
        if (MySharedPreference.getFavoriteList()!!.contains(code!!)) {
            binding.addToWishList.setImageResource(R.drawable.fav)
        }

    }

}
