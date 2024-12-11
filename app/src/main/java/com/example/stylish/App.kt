package com.example.stylish

import android.app.Application
import com.example.stylish.local.MyDataBase
import com.example.stylish.utils.MySharedPreference
import com.google.firebase.FirebaseApp

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        MySharedPreference.init(this)
        MyDataBase.initRoomDataBase(this)
        FirebaseApp.initializeApp(this)

    }
}