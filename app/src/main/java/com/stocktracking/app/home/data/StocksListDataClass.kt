package com.stocktracking.app.home.data

import com.google.gson.annotations.SerializedName

data class StocksListDataClass(
    @SerializedName("success") val success : Boolean,
    @SerializedName("data") val data : List<StockData>)
