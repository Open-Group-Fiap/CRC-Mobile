package br.com.opengroup.crc.models

data class User(
    val email: String,
    val password: String,
    val confirmPassword: String,
    val cpf: String,
    val condoCode: Int,
    val name: String,
    val residence: String,
    val numPeople: Int
)