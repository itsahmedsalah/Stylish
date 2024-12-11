package com.example.stylish.ui.homepage.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.stylish.adapters.CategoryProductAdapter
import com.example.stylish.databinding.FragmentSearchBinding
import com.example.stylish.models.ProductModel
import com.example.stylish.ui.homepage.home.HomePageViewModel


class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private var categoryProductAdapter: CategoryProductAdapter? = null
    private val homePageViewModel: HomePageViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        homePageViewModel.getProducts()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentSearchBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        bindProducts()
    }

    private fun bindProducts() {
        homePageViewModel.productList.observe(requireActivity(),{
            categoryProductAdapter = CategoryProductAdapter(it)
            binding.rvProducts.adapter = categoryProductAdapter
        })
    }


}