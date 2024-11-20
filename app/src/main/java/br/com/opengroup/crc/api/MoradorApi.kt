package br.com.opengroup.crc.api

import br.com.opengroup.crc.models.Fatura
import br.com.opengroup.crc.models.Morador
import br.com.opengroup.crc.models.network.LoginRequest
import br.com.opengroup.crc.models.network.MoradorRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface MoradorApi {
    @POST("/login")
    suspend fun login(@Body login: LoginRequest): Response<Void>

    @POST("/morador")
    suspend fun register(@Body morador: MoradorRequest): Response<Morador>

    @GET("/morador/email/{email}")
    suspend fun getMoradorByEmail(@Path("email") email: String): Response<Morador>

    @PUT("/morador/{id}")
    suspend fun updateMorador(
        @Path("id") id: String,
        @Body morador: MoradorRequest
    ): Response<Morador>

    @DELETE("/morador/{id}")
    suspend fun deleteMorador(@Path("id") id: String): Response<Void>

}