package com.jeebley.task.api.service

import com.jeebley.task.api.responce.Response
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query


interface UserService {
    // User API
    @GET("services.php")
    fun getStore(@Query("action") action: String,
                 @Query("langId") langId: Int,
                 @Query("countryId") countryId: Int,
                 @Query("areaId") areaId: Int,
                 @Query("rId") rId: Int): Observable<Response.StoreDetails>

    @GET("services.php")
    fun getDetailed(@Query("action") action: String,
                    @Query("rId") rId: String?,
                    @Query("cuisineType") cuisinetype: Int,
                    @Query("countryId") countryId: Int,
                    @Query("langId") langId: Int
    ): Observable<Response.ListCategory>


}