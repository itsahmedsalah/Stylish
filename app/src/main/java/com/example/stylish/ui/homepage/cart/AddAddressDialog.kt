package com.example.stylish.ui.homepage.cart

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.example.stylish.databinding.AddAddressDialogBinding
import com.example.stylish.utils.MySharedPreference

class AddAddressDialog(context: Context) : Dialog(context) {

    private lateinit var binding: AddAddressDialogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AddAddressDialogBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    override fun onStart() {
        super.onStart()
        onCLick()
    }

    private fun onCLick() {
        binding.btnSave.setOnClickListener {
            val address = binding.etAddress.text.toString()
            val city = binding.etCity.text.toString()
            val country = binding.etCountry.text.toString()
            validation(address, city, country)
        }
    }

    private fun validation(address: String, city: String, country: String) {
        if (address.isEmpty()) {
            binding.etAddress.error = "Add your address in details"
        } else if (city.isEmpty()) {
            binding.etCity.error = "What is your city?"
        } else if (country.isEmpty()) {
            binding.etCountry.error = "What is your country?"
        } else {
            addToSharePreference(address, city, country)
        }
    }

    private fun addToSharePreference(address: String, city: String, country: String) {
        MySharedPreference.setUserAddress(address)
        MySharedPreference.setUserCity(city)
        MySharedPreference.setUserCountry(country)
        dismiss()
    }


}