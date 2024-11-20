package br.com.opengroup.crc.models.network

data class MoradorBonusRequest(
    val idBonus: Int,
    val idMorador: Int,
    val qtd: Int
)