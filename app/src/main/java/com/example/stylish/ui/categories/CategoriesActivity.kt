package com.example.stylish.ui.categories

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels

import androidx.appcompat.app.AppCompatActivity
import com.example.stylish.adapters.CategoryProductAdapter

import com.example.stylish.databinding.ActivityCategoriesBinding
import com.example.stylish.models.ProductModel
import com.example.stylish.ui.homepage.home.HomePageViewModel
import com.example.stylish.ui.productdetails.ProductDetailsActivity
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class CategoriesActivity : AppCompatActivity() {


    private var categoryProductAdapter: CategoryProductAdapter? = null
    private lateinit var binding: ActivityCategoriesBinding
    private val homePageViewModel: HomePageViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoriesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val type = intent.getStringExtra("type")

        homePageViewModel.getProducts()
        homePageViewModel.productList.observe(this) {
            val list = it.filter { it.customerGroup == type }
            setBinds(list)
        }
        onCLick()

    }

    private fun onCLick() {
        categoryProductAdapter?.setOnClickListener(object : CategoryProductAdapter.OnItemClick {
            override fun onClick(product: ProductModel) {
                val intent = Intent(this@CategoriesActivity, ProductDetailsActivity::class.java)
                val json = Json.encodeToString(product)
                intent.putExtra("product", json)
                startActivity(intent)

            }

        })
    }

    private fun setBinds(list: List<ProductModel>) {
        categoryProductAdapter = CategoryProductAdapter(list)
        binding.rvCategories.adapter = categoryProductAdapter
        binding.tvItemCount.text = "${list.size - 1}+ Iteams"
        onCLick()
    }
}