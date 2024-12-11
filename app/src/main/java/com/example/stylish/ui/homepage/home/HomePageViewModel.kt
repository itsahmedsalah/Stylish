package com.example.stylish.ui.homepage.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stylish.models.CategoriesModel
import com.example.stylish.models.ProductModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomePageViewModel : ViewModel() {

    private val HomePageRepository = HomePageRepository()

    private val _gotCategorySuccess = MutableLiveData<Boolean>()
    val gotCategorySuccess: LiveData<Boolean> get() = _gotCategorySuccess

    private val _categoriesList = MutableLiveData<List<CategoriesModel>>()
    val categoriesList: LiveData<List<CategoriesModel>> get() = _categoriesList

    private val _gotProductSuccess = MutableLiveData<Boolean>()
    val gotProductSuccess: LiveData<Boolean> get() = _gotProductSuccess

    private val _productsList = MutableLiveData<List<ProductModel>>()
    val productList: LiveData<List<ProductModel>> get() = _productsList

    fun getCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _categoriesList.postValue(HomePageRepository.getCategories())
                _gotCategorySuccess.postValue(true)


            } catch (e: Exception) {
                _gotCategorySuccess.postValue(false)
            }
        }
    }


    fun getProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _productsList.postValue(HomePageRepository.getProducts())
                _gotProductSuccess.postValue(true)
            } catch (e: Exception) {
                _gotProductSuccess.postValue(false)
            }
        }
    }
}