package br.com.opengroup.crc.models

data class Morador(
    val id: String,
    val nome: String,
    val auth: Auth,
    val cpf: String,
    val pontos: Int,
    val condominio: Condominio,
    val identificadorRes: String,
    val qtdMoradores: Int
)