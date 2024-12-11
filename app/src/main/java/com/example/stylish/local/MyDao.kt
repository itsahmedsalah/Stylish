package com.example.stylish.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.stylish.models.ProductModel

@Dao
interface MyDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertProduct(productModel: ProductModel)

    @Delete
    fun deleteProduct(productModel: ProductModel)


    @Query("select * from products")
    fun getAllFavoriteProducts():LiveData<List<ProductModel>>

}