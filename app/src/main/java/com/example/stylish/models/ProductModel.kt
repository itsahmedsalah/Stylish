package com.example.stylish.models

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity(tableName = "products")
@Serializable
data class ProductModel(
    @PrimaryKey(autoGenerate = false)
    val code: String,
    val productName: String?,
    val stockLevel: Int?,
    val productPrice: Int?,
    val productImage: List<String>?,
    val productBrand: String?,
    val productDescription: String?,
    val customerGroup: String?,
    val supCategory: String?,
    val productDiscount: Int?,
)


