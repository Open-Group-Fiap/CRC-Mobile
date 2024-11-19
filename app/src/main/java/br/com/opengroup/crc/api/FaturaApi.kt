package br.com.opengroup.crc.api

import br.com.opengroup.crc.models.Fatura
import br.com.opengroup.crc.models.network.FaturaPage
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface FaturaApi {
    @GET("/randomFatura/{id}")
    suspend fun randomFatura(@Path("id") id: String): Response<Fatura>
    
    @GET("/fatura/morador/{id}")
    suspend fun getFaturas(
        @Path("id") id: String,
        @Query("pageNumber") pageNumber: Int,
        @Query("pageSize") pageSize: Int
    ): Response<FaturaPage>
}