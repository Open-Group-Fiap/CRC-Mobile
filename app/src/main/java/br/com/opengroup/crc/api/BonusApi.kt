package br.com.opengroup.crc.api

import br.com.opengroup.crc.models.Bonus
import retrofit2.Response
import retrofit2.http.GET

interface BonusApi {
    @GET("/bonus/condominio/{id}")
    suspend fun getBonusByCondominio(id: String): Response<List<Bonus>>
}