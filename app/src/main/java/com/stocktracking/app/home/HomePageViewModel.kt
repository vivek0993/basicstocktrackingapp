package com.stocktracking.app.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.stocktracking.app.home.data.StockData

open class HomePageViewModel(applicationContext: Application) : AndroidViewModel(applicationContext) {
    private val homePageRepository = HomePageRepository()
    open val stocksLiveData = MutableLiveData<List<StockData>>()

    open fun getStocksLiveData(): LiveData<List<StockData>> {
        return stocksLiveData
    }

    open fun getStocksData() {
    homePageRepository.getStocksData(stocksLiveData)
    }

}