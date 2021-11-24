package com.stocktracking.app.home

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.stocktracking.app.R
import com.stocktracking.app.home.adapter.HomePageAdapter


open class HomePageFragment : Fragment() {

    private val homePageViewModel: HomePageViewModel by lazy {
        getViewModelProvider(this, null).get(HomePageViewModel::class.java)
    }
    private var customAdapter: HomePageAdapter? = null

    private var listView : ListView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home_page, container, false)
        listView = view.findViewById(R.id.lv_stocks_list)
        listView!!.adapter = null

        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val handler = Handler(Looper.getMainLooper())
        handler.post(object : Runnable {
            override fun run() {
                homePageViewModel.getStocksData()
                handler.postDelayed(this, 5000)
            }
        })

        homePageViewModel.stocksLiveData.observe(viewLifecycleOwner, Observer {

            if(it != null) {

                if(customAdapter == null) {
                    customAdapter = HomePageAdapter(context, it)
                    listView!!.adapter = customAdapter
                } else {
                    customAdapter!!.updateData(it)
                    customAdapter!!.notifyDataSetChanged()
                }

            }
        })
    }

    open fun getViewModelProvider(
        fragment: Fragment,
        factory: ViewModelProvider.Factory?
    ): ViewModelProvider {
        return ViewModelProviders.of(fragment, factory)
    }

}