package com.example.stylish.ui.homepage.search

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.fragment.app.viewModels
import com.example.stylish.adapters.CategoryProductAdapter
import com.example.stylish.databinding.FragmentSearchBinding
import com.example.stylish.models.ProductModel

import com.example.stylish.ui.homepage.home.HomePageViewModel
import com.example.stylish.ui.productdetails.ProductDetailsActivity
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.Locale


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
        onClick()
    }

    private fun onClick() {
        categoryProductAdapter?.setOnClickListener(object : CategoryProductAdapter.OnItemClick {
            override fun onClick(product: ProductModel) {
                val intent = Intent(requireActivity(), ProductDetailsActivity::class.java)
                val json = Json.encodeToString(product)
                intent.putExtra("product", json)
                startActivity(intent)
            }

        })
    }

    private fun bindProducts() {
        homePageViewModel.productList.observe(requireActivity(), {
            categoryProductAdapter = CategoryProductAdapter(it)
            binding.rvProducts.adapter = categoryProductAdapter
        })

        binding.searchView.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }

        })
    }

    private fun filterList(query: String?) {

        if (query != null) {
            val filteredList = ArrayList<ProductModel>()
            for (i in homePageViewModel.productList.value!!) {
                if (i.productName!!.lowercase(Locale.ROOT).contains(query)) {
                    filteredList.add(i)
                }
            }

            if (filteredList.isNotEmpty()) {
                categoryProductAdapter?.setFilteredList(filteredList)
            }
        }


    }


}