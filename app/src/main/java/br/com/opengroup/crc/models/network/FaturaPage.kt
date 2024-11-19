package br.com.opengroup.crc.models.network

import br.com.opengroup.crc.models.Fatura

data class FaturaPage(
    val pageNumber: Int,
    val pageSize: Int,
    val total: Int,
    val faturas: List<Fatura>
)