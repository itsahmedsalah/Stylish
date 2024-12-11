package com.example.stylish.ui.auth

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.provider.CalendarContract.Colors
import android.util.Log
import android.view.View

import com.example.stylish.databinding.ChooseAvatarDialogBinding

class ChooseAvatarDialog(context: Context) : Dialog(context) {

    private lateinit var binding: ChooseAvatarDialogBinding
    var avatarSelectedListener: OnAvatarSelectedListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ChooseAvatarDialogBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }

    override fun onStart() {
        super.onStart()
        onClick()
    }

    private fun onClick() {
        binding.imgBoy.setOnClickListener {
            avatarSelectedListener?.onAvatarSelected("https://ganwmjesjsawpqznnpcs.supabase.co/storage/v1/object/public/avatars/boy_avatar.jpg")
            dismiss()
        }
        binding.imgHacker.setOnClickListener {
            avatarSelectedListener?.onAvatarSelected("https://ganwmjesjsawpqznnpcs.supabase.co/storage/v1/object/public/avatars/hackar_avatar.jpg")
            dismiss()
        }

        binding.imgKratos.setOnClickListener {
            avatarSelectedListener?.onAvatarSelected("https://ganwmjesjsawpqznnpcs.supabase.co/storage/v1/object/public/avatars/kratos_avatar.jpg")

            dismiss()
        }

        binding.imgWomen.setOnClickListener {
            avatarSelectedListener?.onAvatarSelected("https://ganwmjesjsawpqznnpcs.supabase.co/storage/v1/object/public/avatars/women_avatar.jpg")
            dismiss()
        }
        binding.imgMen.setOnClickListener {
            avatarSelectedListener?.onAvatarSelected("https://ganwmjesjsawpqznnpcs.supabase.co/storage/v1/object/public/avatars/men_vector.jpg")
            dismiss()
        }
    }


    interface OnAvatarSelectedListener {
        fun onAvatarSelected(avatarImage: String)
    }
}