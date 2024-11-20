package br.com.opengroup.crc.api

import br.com.opengroup.crc.models.Bonus
import br.com.opengroup.crc.models.network.MoradorBonusRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface BonusApi {
    @GET("/bonus/avaliable/condominio/{id}")
    suspend fun getBonusByCondominio(
        @Path("id") id: String,
        @Query("pageSize") pageSize: Int = 100
    ): Response<List<Bonus>>

    @POST("/moradorbonus")
    suspend fun comprarBonus(@Body request: MoradorBonusRequest): Response<Void>
    
}