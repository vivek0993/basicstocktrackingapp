package com.stocktracking.app.util

import com.stocktracking.app.home.data.StocksListDataClass
import retrofit2.Call
import retrofit2.http.GET


interface ApiInterface {

    companion object {
        const val BASE_URL = "https://api.tickertape.in/"
    }

    @GET("stocks/quotes?sids=RELI,TCS,ITC,HDBK,INFY")
    fun doGetStockData(): Call<StocksListDataClass>
}