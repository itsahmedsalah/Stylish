package com.example.stylish.ui.homepage.setting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.stylish.databinding.FragmentSettingPageBinding
import com.example.stylish.utils.MySharedPreference

class SettingPageFragment : Fragment() {


    private lateinit var binding: FragmentSettingPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentSettingPageBinding.inflate(layoutInflater, container, false)
        checkAddress()

        return binding.root
    }

    private fun checkAddress() {
        if (MySharedPreference.isAddressStored()) {
            binding.etUserAddress.text = MySharedPreference.getUserAddress()
            binding.etUserCity.text = MySharedPreference.getUserCity()
            binding.etUserCountry.text = MySharedPreference.getUserCountry()
        }
        binding.etUserEmail.text = MySharedPreference.getUserEmail()
        Glide.with(binding.root).load(MySharedPreference.getUserAvatar()).into(binding.avatarUser)
    }
}