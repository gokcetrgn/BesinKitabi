package com.gokcenaztorgan.besinkitabi.service

import com.gokcenaztorgan.besinkitabi.model.Besin
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class BesinApiServis {

    private val api = Retrofit.Builder().baseUrl("https://raw.githubusercontent.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(BesinAPI::class.java)

    suspend fun getData() : List<Besin> {
        return api.getBesin()
    }

}