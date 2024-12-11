package com.example.stylish.models

import kotlinx.serialization.Serializable

@Serializable
data class CategoriesModel(
    val categoryId: Int?,
    val imageUrl: String?,
    val title: String?,
)
