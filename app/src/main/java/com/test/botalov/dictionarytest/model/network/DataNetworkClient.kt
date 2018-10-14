package com.test.botalov.dictionarytest.model.network

import com.test.botalov.dictionarytest.R
import com.test.botalov.dictionarytest.application.App
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit


class DataNetworkClient private constructor() {

    var retrofit: Retrofit? = null
    init {
        val builder = OkHttpClient.Builder()
        val okHttpClient = builder
                .writeTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .build()

        val baseUrl = App.context!!.getString(R.string.base_url)

        retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build()
    }

    private object Holder {
        val INSTANCE = DataNetworkClient()
    }
    companion object {
        val instance: DataNetworkClient by lazy { Holder.INSTANCE }
    }
}