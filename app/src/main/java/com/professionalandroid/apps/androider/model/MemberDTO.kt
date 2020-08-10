package com.professionalandroid.apps.androider.model

data class MemberDTO(
    val id: Int,
    val userID: String,
    val password: String,
    val email: String,
    val imageURL: String,
    val nickname: String
)