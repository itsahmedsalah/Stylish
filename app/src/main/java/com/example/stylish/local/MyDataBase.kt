package com.example.stylish.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.stylish.models.ProductModel
import com.example.stylish.utils.Converters

@Database(entities = [ProductModel::class], version = 1)
@TypeConverters(Converters::class)
abstract class MyDataBase : RoomDatabase() {

    abstract fun myDao(): MyDao


    companion object {
        var favDataBase: MyDataBase? = null
        var cartDataBase: MyDataBase? = null
        fun initRoomDataBase(context: Context) {
            if (favDataBase == null || cartDataBase == null) {
                favDataBase = Room.databaseBuilder(
                    context,
                    MyDataBase::class.java,
                    "fav_product"
                ).build()
                cartDataBase = Room.databaseBuilder(
                    context,
                    MyDataBase::class.java,
                    "cart_product"
                ).build()

            }
        }
    }

}