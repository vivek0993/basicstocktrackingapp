package com.stocktracking.app.home

import androidx.lifecycle.MutableLiveData
import com.stocktracking.app.home.data.StockData
import com.stocktracking.app.home.data.StocksListDataClass
import com.stocktracking.app.util.RetrofitClientClass
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


open class HomePageRepository {


    open fun getStocksData( stocksData: MutableLiveData<List<StockData>>){
        val call: Call<StocksListDataClass> = RetrofitClientClass.getInstance().myApi.doGetStockData()

        call.enqueue(object : Callback<StocksListDataClass> {
            override fun onResponse(call: Call<StocksListDataClass>, response: Response<StocksListDataClass>) {

                val stocksDataList: StocksListDataClass? = response.body()

                stocksData.postValue(stocksDataList?.data)

            }

            override fun onFailure(call: Call<StocksListDataClass>, t: Throwable) {
                stocksData.postValue(null)

            }
        })
    }


}




