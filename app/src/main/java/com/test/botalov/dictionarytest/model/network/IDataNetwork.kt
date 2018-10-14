package com.test.botalov.dictionarytest.model.network

import com.test.botalov.dictionarytest.model.network.entities.TranslateData
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query


interface IDataNetwork {
    @GET("api/v1/dicservice.json/lookup")
    fun getTranslate(@Query("key") key: String, @Query("text") text: String, @Query("lang") lang: String): Observable<TranslateData>
}