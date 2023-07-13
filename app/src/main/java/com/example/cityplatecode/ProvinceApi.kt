package com.example.cityplatecode

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

public interface ProvinceApi {
    @GET("api/v1/provinces/{id}")
    suspend fun getProvince(@Path("id") id:Int): Response<ProvinceResponse>
}