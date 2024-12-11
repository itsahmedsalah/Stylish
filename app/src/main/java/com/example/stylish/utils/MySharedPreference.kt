package com.example.stylish.utils

import android.content.Context
import android.content.SharedPreferences

object MySharedPreference {

    private const val SHARED_PREFERENCES_NAME = "user-data"
    private var mAppContext: Context? = null
    private const val USER_NAME = "user name"
    private const val USER_EMAIL = "user email"
    private const val USER_ID = "user id"
    private const val USER_AVATAR = "user avatar"
    private const val FAV_SET = "fav set"
    private const val COUNTRY = "country"
    private const val CITY = "city"
    private const val ADDRESS = "address"

    fun init(appContext: Context?) {
        mAppContext = appContext

    }

    private fun getSharedPreferences(): SharedPreferences {
        return mAppContext!!.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
    }


    fun addToFavoriteList(code: String) {
        val editor = getSharedPreferences().edit()
        val currentFavorites =
            getSharedPreferences().getStringSet(FAV_SET, emptySet())?.toMutableSet()
        currentFavorites?.add(code)

        editor.putStringSet(FAV_SET, currentFavorites).apply()
    }

    fun removeFromFavoriteList(code: String) {
        val editor = getSharedPreferences().edit()
        val currentFavorites =
            getSharedPreferences().getStringSet(FAV_SET, emptySet())?.toMutableSet()
        currentFavorites?.remove(code)

        editor.putStringSet(FAV_SET, currentFavorites).apply()
    }

    fun getFavoriteList(): MutableSet<String>? {
        return getSharedPreferences().getStringSet(FAV_SET, emptySet())
    }


    fun setUserName(container: String) {

        val editor = getSharedPreferences().edit()
        editor.putString(USER_NAME, container).apply()
    }

    fun getUserName(): String {

        return getSharedPreferences().getString(USER_NAME, null)!!
    }

    fun setUserID(container: String) {

        val editor = getSharedPreferences().edit()
        editor.putString(USER_ID, container).apply()
    }

    fun getUserID(): String {

        return getSharedPreferences().getString(USER_ID, null)!!
    }

    fun setUserEmail(container: String) {

        val editor = getSharedPreferences().edit()
        editor.putString(USER_EMAIL, container).apply()
    }

    fun getUserEmail(): String {

        return getSharedPreferences().getString(USER_EMAIL, null)!!
    }


    fun setUserAvatar(container: String) {

        val editor = getSharedPreferences().edit()
        editor.putString(USER_AVATAR, container).apply()
    }

    fun getUserAvatar(): String {

        return getSharedPreferences().getString(USER_AVATAR, null)!!
    }


    fun setUserCountry(container: String) {

        val editor = getSharedPreferences().edit()
        editor.putString(COUNTRY, container).apply()
    }

    fun getUserCountry(): String {

        return getSharedPreferences().getString(COUNTRY, null)!!
    }

    fun setUserCity(container: String) {

        val editor = getSharedPreferences().edit()
        editor.putString(CITY, container).apply()
    }

    fun getUserCity(): String {

        return getSharedPreferences().getString(CITY, null)!!
    }


    fun setUserAddress(container: String) {

        val editor = getSharedPreferences().edit()
        editor.putString(ADDRESS, container).apply()
    }

    fun getUserAddress(): String {

        return getSharedPreferences().getString(ADDRESS, null)!!
    }

    fun isAddressStored():Boolean{
        return getSharedPreferences().contains(ADDRESS)
    }
}