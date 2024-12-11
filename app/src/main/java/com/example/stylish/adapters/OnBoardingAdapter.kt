package com.example.stylish.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.stylish.databinding.OnboardingItemBinding
import com.example.stylish.models.OnBoardingModel
import com.example.stylish.ui.splash.onbording.OnBordingActivity

class OnBoardingAdapter(private val list: List<OnBoardingModel>) :
    RecyclerView.Adapter<OnBoardingAdapter.Holder>() {

    inner class Holder(val binding: OnboardingItemBinding) : ViewHolder(binding.root) {

        fun setBind(page: OnBoardingModel) {

            binding.imageView.setImageResource(page.image)
            binding.tvTitle.setText(page.title)
            binding.tvSub.setText(page.subTitle)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding =
            OnboardingItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.setBind(list[position])
    }
}