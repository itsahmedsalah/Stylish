package com.example.stylish.ui.homepage.home

import com.example.stylish.models.CategoriesModel
import com.example.stylish.models.ProductModel
import com.example.stylish.utils.SupaBase
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.Columns

class HomePageRepository {

    suspend fun getCategories(): List<CategoriesModel> {
        val data = SupaBase.supabase.postgrest["categories"].select(Columns.ALL)
        val response = data.decodeList<CategoriesModel>()
        return response
    }

    suspend fun getProducts(): List<ProductModel> {
        val products = SupaBase.supabase.postgrest["products"].select(Columns.ALL)
        val response = products.decodeList<ProductModel>()
        return response
    }



}