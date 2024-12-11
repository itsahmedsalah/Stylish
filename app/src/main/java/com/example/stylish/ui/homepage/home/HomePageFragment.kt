package com.example.stylish.ui.homepage.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels

import com.example.stylish.adapters.CategoryAdapter
import com.example.stylish.adapters.ProductAdapter
import com.example.stylish.databinding.FragmentHomePageBinding
import com.example.stylish.local.MyDataBase
import com.example.stylish.models.ProductModel
import com.example.stylish.ui.categories.CategoriesActivity
import com.example.stylish.ui.productdetails.ProductDetailsActivity
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


class HomePageFragment : Fragment() {


    private lateinit var binding: FragmentHomePageBinding
    private var categoryAdapter: CategoryAdapter? = null
    private var productAdapter: ProductAdapter? = null
    private val homePageViewModel: HomePageViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        homePageViewModel.getCategories()
        homePageViewModel.getProducts()
        binding = FragmentHomePageBinding.inflate(layoutInflater, container, false)
        return binding.root
    }



    override fun onStart() {
        super.onStart()
        bindCategory()
        bindProducts()
        onClick()
        Log.d("", "onViewCreated: ")
    }


    private fun bindCategory() {
        homePageViewModel.categoriesList.observe(requireActivity()) {
            categoryAdapter = CategoryAdapter(it)
            binding.rvCatefory.adapter = categoryAdapter
            onClick()
        }

    }

    private fun bindProducts() {
        homePageViewModel.productList.observe(requireActivity()) {
            getDealOfTheDay(it)
            getTrendingProduct(it)
            onClick()
        }
    }

    private fun getDealOfTheDay(list: List<ProductModel>) {
        val productList = list.filter { it.supCategory == "Deal of the Day" }
        productAdapter = ProductAdapter(productList)
        binding.rvDealOfTheDay.adapter = productAdapter
        onClick()
    }

    private fun getTrendingProduct(list: List<ProductModel>) {
        val productList = list.filter { it.supCategory == "Trending Products" }
        productAdapter = ProductAdapter(productList)
        binding.rvTrendingProduct.adapter = productAdapter
        onClick()
    }

    private fun onClick() {
        categoryAdapter?.setOnClickListener(object : CategoryAdapter.OnItemClick {
            override fun onClickListener(type: String) {
                val intent = Intent(requireActivity(), CategoriesActivity::class.java)
                intent.putExtra("type", type)
                startActivity(intent)
            }

        })
        productAdapter?.setOnClickListener(object : ProductAdapter.OnItemClick {
            override fun onClick(product: ProductModel) {
                val intent = Intent(requireActivity(), ProductDetailsActivity::class.java)
                val json = Json.encodeToString(product)
                intent.putExtra("product", json)
                startActivity(intent)
            }
        })

    }

}

