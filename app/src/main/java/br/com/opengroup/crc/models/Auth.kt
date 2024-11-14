package br.com.opengroup.crc.models

data class Auth(
    val token: String,
    val refreshToken: String
)