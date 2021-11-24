package com.stocktracking.app.wishlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.stocktracking.app.R


class WishlistFragment : Fragment() {

    private var listView : ListView? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_wishlist, container, false)
        listView = view.findViewById(R.id.lv_stocks_list)
        listView!!.adapter = null

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(FirebaseAuth.getInstance().currentUser != null) {
            val userId = FirebaseAuth.getInstance().currentUser!!.uid

            FirebaseFirestore.getInstance().collection("wishlist").document(userId).get()
                .addOnSuccessListener {

                    if (it != null) {
                        val dataList = ArrayList<String>()

                        if(it.data != null) {
                            for ((key, value) in it.data!!) {
                                dataList.add(value.toString())
                            }

                            val arrayAdapter = ArrayAdapter<String>(
                                requireContext(),
                                R.layout.wishlist_item_view, R.id.itemTextView, dataList
                            )
                            listView!!.setAdapter(arrayAdapter)
                        }

                    }
                }
        }


    }
}