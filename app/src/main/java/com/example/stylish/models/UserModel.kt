package com.example.stylish.models

import kotlinx.serialization.Serializable

@Serializable
data class UserModel(
    var userId: String?=null,
    val userName: String?=null,
    val userEmail: String?=null,
    val userAvatar: String?=null,
)