package com.example.stylish.ui.homepage.wishlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.stylish.adapters.CategoryProductAdapter
import com.example.stylish.databinding.FragmentWishListBinding
import com.example.stylish.local.MyDataBase

class WishListFragment : Fragment() {

    private lateinit var binding: FragmentWishListBinding
    private var categoryProductAdapter: CategoryProductAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentWishListBinding.inflate(layoutInflater, container, false)
        MyDataBase.favDataBase?.myDao()?.getAllFavoriteProducts()?.observe(requireActivity(), {
            if (it.isNotEmpty()) {
                binding.tvIsEmpty.visibility = View.GONE
                categoryProductAdapter = CategoryProductAdapter(it)
                binding.rvFavProduct.adapter = categoryProductAdapter
            }
        })

        return binding.root
    }


}