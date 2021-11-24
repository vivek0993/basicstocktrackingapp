package com.stocktracking.app.home.data

import com.google.gson.annotations.SerializedName

data class StockData (
	@SerializedName("sid") val sid : String,
	@SerializedName("price") val price : Double,
	@SerializedName("close") val close : Double,
	@SerializedName("change") val change : Double,
	@SerializedName("high") val high : Double,
	@SerializedName("low") val low : Double,
	@SerializedName("volume") val volume : Double,
	@SerializedName("date") val date : String
)