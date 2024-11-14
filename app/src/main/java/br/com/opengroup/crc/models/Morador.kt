package br.com.opengroup.crc.models

data class Morador(
    val id: String,
    val nome: String,
    val email: String,
    val telefone: String,
    val auth: Auth,
    val condominio: Condominio
)