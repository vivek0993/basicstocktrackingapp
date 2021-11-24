package com.stocktracking.app.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.stocktracking.app.R
import com.stocktracking.app.home.data.StockData


class HomePageAdapter(
    applicationContext: Context?,
    stockDataList: List<StockData>?,
) : BaseAdapter() {

    private var context = applicationContext
    private var stockDataList = stockDataList
    private var inflater = LayoutInflater.from(applicationContext)

    fun updateData (updatedStockDataList: List<StockData>?) {
        this.stockDataList = updatedStockDataList
    }

    override fun getCount(): Int {
        return stockDataList!!.size
    }

    override fun getItem(position: Int): Any {
        return stockDataList!![position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = inflater!!.inflate(R.layout.layout_stocks_list, null)
        val sid = view.findViewById<TextView>(R.id.tv_sid)
        val price = view.findViewById<TextView>(R.id.tv_price)
        val checkBox = view.findViewById<CheckBox>(R.id.cb_wishlist)
        val gainLossIndicator = view.findViewById<ImageView>(R.id.iv_gain_loss)

        checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
            if(FirebaseAuth.getInstance().currentUser != null) {
                val userId = FirebaseAuth.getInstance().currentUser!!.uid
                var wishlistData = hashMapOf(
                    stockDataList?.get(position)!!.sid to stockDataList?.get(position)!!.sid
                )
                if (isChecked) {
                    FirebaseFirestore.getInstance().collection("wishlist").document(userId)
                        .set(
                            wishlistData,
                            SetOptions.merge()
                        )
                } else {
                    val docRef =
                        FirebaseFirestore.getInstance().collection("wishlist").document(userId)

                    val updates = hashMapOf<String, Any>(
                        stockDataList?.get(position)!!.sid to FieldValue.delete()
                    )
                    docRef.update(updates).addOnCompleteListener { }
                }
            }
        }

        sid.text = stockDataList?.get(position)!!.sid
        price.text = stockDataList?.get(position)!!.price.toString()
        val changeInPrice = stockDataList?.get(position)!!.change

        if(changeInPrice >= 0) {
           gainLossIndicator.setImageDrawable(context!!.resources.getDrawable(R.drawable.ic_arrow_up_24))
        } else {
            gainLossIndicator.setImageDrawable(context!!.resources.getDrawable(R.drawable.ic_arrow_down_24))
        }

        return view
    }


}