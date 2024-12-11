package com.example.stylish.ui.splash.onbording

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope

import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import androidx.viewpager2.widget.ViewPager2
import com.example.stylish.R
import com.example.stylish.adapters.OnBoardingAdapter

import com.example.stylish.databinding.ActivityOnBordingBinding
import com.example.stylish.models.OnBoardingModel
import com.example.stylish.ui.auth.LoginActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class OnBordingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnBordingBinding
    private val list = listOf(
        OnBoardingModel(
            R.drawable.bording1, R.string._choose_products, R.string._onbording1_sub
        ),
        OnBoardingModel(
            R.drawable.bording2, R.string._make_payment, R.string._onbording1_sub
        ),
        OnBoardingModel(
            R.drawable.bording3, R.string._get_your_order, R.string._onbording1_sub
        ),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnBordingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = OnBoardingAdapter(list)
        binding.viewPager.adapter = adapter
        binding.dotsIndicator.attachTo(binding.viewPager)
        onCLick()
    }

    private fun onCLick() {
        binding.btnNext.setOnClickListener {
            val currentItem = binding.viewPager.currentItem

            if (currentItem < list.size - 1) {
                binding.viewPager.currentItem = currentItem + 1
            } else {
                val intent = Intent(this@OnBordingActivity, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.tvPageNum.text = "${position + 1}/3"
                if (position == list.size - 1) {
                    binding.btnNext.text = "Submit"
                } else {
                    binding.btnNext.text = "Next"
                }
            }

        })

        binding.btnSkip.setOnClickListener {
            binding.viewPager.currentItem = list.size - 1
            lifecycleScope.launch(Dispatchers.Main) {
                delay(300)
                val intent = Intent(this@OnBordingActivity, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

}