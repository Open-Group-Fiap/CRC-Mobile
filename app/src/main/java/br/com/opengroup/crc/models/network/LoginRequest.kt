package br.com.opengroup.crc.models.network

data class LoginRequest(
    val email: String,
    val senha: String
)