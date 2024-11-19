package br.com.opengroup.crc.models.network

data class MoradorRequest (
    val idCondominio: Int,
    val email: String,
    val senha: String,
    val cpf: String,
    val nome: String,
    val qtdMoradores: Int,
    val identificadorRes: String
)