package br.com.opengroup.crc.models

data class Bonus(
    val id: Int,
    val condominio: Condominio,
    val nome: String,
    val descricao: String,
    val custo: Int,
    val qtdMax: Int
)