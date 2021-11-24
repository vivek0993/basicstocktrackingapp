package com.stocktracking.app.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.stocktracking.app.R

class UserProfileFragment : Fragment() {

    private lateinit var userName: TextView
    private lateinit var logout: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_user_profile, container, false)
        userName = view.findViewById(R.id.tv_user_name)
        logout = view.findViewById(R.id.tv_logout)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(FirebaseAuth.getInstance().currentUser != null) {
            val userId = FirebaseAuth.getInstance().currentUser!!.uid

            FirebaseFirestore.getInstance().collection("users").document(userId).get()
                .addOnSuccessListener {

                    if (it != null && it.data != null) {
                        userName.text = it.getString("name")!!.uppercase()
                    }
                }
        }

        logout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val navOptions = NavOptions.Builder()
                .setPopUpTo(R.id.dest_userProfile, true)
                .build()
            findNavController().navigate(R.id.dest_login,null, navOptions)
        }


    }
}