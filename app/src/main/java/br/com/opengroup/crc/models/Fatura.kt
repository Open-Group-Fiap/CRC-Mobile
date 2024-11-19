package br.com.opengroup.crc.models

data class Fatura(
    val id: Int,
    val idMorador: Int,
    val qtdConsumida: Int,
    val dtGeracao: String
)