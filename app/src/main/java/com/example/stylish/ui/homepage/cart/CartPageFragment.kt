package com.example.stylish.ui.homepage.cart

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.lifecycle.lifecycleScope
import com.example.stylish.R
import com.example.stylish.adapters.CartProductAdapter

import com.example.stylish.databinding.FragmentCartPageBinding
import com.example.stylish.local.MyDataBase
import com.example.stylish.models.ProductModel
import com.example.stylish.ui.auth.ChooseAvatarDialog
import com.example.stylish.utils.MySharedPreference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CartPageFragment : Fragment() {

    private lateinit var binding: FragmentCartPageBinding
    private var cartProductAdapter: CartProductAdapter? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentCartPageBinding.inflate(layoutInflater, container, false)
        MyDataBase.cartDataBase?.myDao()?.getAllFavoriteProducts()?.observe(requireActivity(), {
            if (it.isEmpty()) {
                binding.tvCartIsEmpty.visibility = View.VISIBLE
            } else {
                binding.tvCartIsEmpty.visibility = View.GONE
                cartProductAdapter = CartProductAdapter(it)
                binding.rvCartList.adapter = cartProductAdapter
                onClick()
            }
        })
        onClick()
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    private fun checkAddress() {
        if (MySharedPreference.isAddressStored()) {
            binding.tvAddressDetails.text =
                "${MySharedPreference.getUserAddress()},${MySharedPreference.getUserCity()}, ${MySharedPreference.getUserCountry()} "
        }
    }

    private fun onClick() {
        cartProductAdapter?.setOnClickListener(object : CartProductAdapter.OnItemClick {
            override fun onDeleteClick(product: ProductModel) {
                lifecycleScope.launch(Dispatchers.IO) {
                    MyDataBase.cartDataBase?.myDao()?.deleteProduct(product)
                }
            }

        })

        binding.editAddress.setOnClickListener {
            val customDialog = AddAddressDialog(requireActivity())
            customDialog.window?.setBackgroundDrawable(
                AppCompatResources.getDrawable(
                    requireActivity(),
                    R.drawable.dialog_bg
                )
            )
            customDialog.setOnDismissListener {
                checkAddress()
            }
            customDialog.show()
        }
    }
}