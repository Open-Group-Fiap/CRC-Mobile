package br.com.opengroup.crc.models

data class Auth(
    val id: Int,
    val email: String,
    val hashSenha: String,
)