package com.example.android.marsrealestate.network

import kotlinx.coroutines.Deferred
import retrofit2.http.GET

interface MarsApi {

       @GET("realestate")
      fun getProperties(/*@Query("filter")type: String*/): Deferred<List<MarsProperty>>
}