package com.zack.proxyserver

import org.json.JSONArray
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("v1/menus")
    fun fetchMenu(): Call<JSONArray>

}