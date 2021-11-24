package com.stocktracking.app

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class LoginFragment : Fragment() {
    private lateinit var firebaseStore: FirebaseFirestore
    private lateinit var auth: FirebaseAuth
    private lateinit var nameEt: EditText
    private lateinit var emailEt: EditText
    private lateinit var passwordEt: EditText

    private lateinit var signUpBtn: Button
    private lateinit var loginBtn: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)
        emailEt = view.findViewById(R.id.email_edt_text)
        passwordEt =view.findViewById(R.id.pass_edt_text)
        nameEt =view.findViewById(R.id.userName_edt_text)

        loginBtn = view.findViewById(R.id.login_btn)
        signUpBtn = view.findViewById(R.id.signup_btn)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.findViewById<BottomNavigationView>(R.id.bottom_nav_view)?.visibility = View.GONE
        auth = FirebaseAuth.getInstance()
        firebaseStore = FirebaseFirestore.getInstance()
        loginBtn.setOnClickListener{
            var email: String = emailEt.text.toString()
            var password: String = passwordEt.text.toString()
            if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                Toast.makeText(context, "Please fill all the fields", Toast.LENGTH_LONG).show()
            } else{
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(activity as Activity, OnCompleteListener { task ->
                    if(task.isSuccessful) {
                        Toast.makeText(context, "Successfully Logged In", Toast.LENGTH_LONG).show()
                        val navOptions = NavOptions.Builder()
                            .setPopUpTo(R.id.dest_login, true)
                            .build()
                        findNavController().navigate(R.id.navigate_to_homePage,null, navOptions)
                        activity?.findViewById<BottomNavigationView>(R.id.bottom_nav_view)?.visibility = View.VISIBLE
                    }else {
                        Toast.makeText(context, "Login Failed", Toast.LENGTH_LONG).show()
                    }
                })
            }
        }

        signUpBtn.setOnClickListener{
            var name: String = nameEt.text.toString()
            var email: String = emailEt.text.toString()
            var password: String = passwordEt.text.toString()

            if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(name)) {
                Toast.makeText(context, "Please fill all the fields", Toast.LENGTH_LONG).show()
            } else{
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(activity as Activity, OnCompleteListener{ task ->
                    if(task.isSuccessful){
                        val userID = auth.currentUser!!.uid
                        val documentReference = firebaseStore.collection("users").document(userID)
                        val data = hashMapOf(
                            "name" to name
                        )
                        documentReference.set(data)

                        Toast.makeText(context, "Successfully Registered", Toast.LENGTH_LONG).show()
                        val navOptions = NavOptions.Builder()
                            .setPopUpTo(R.id.dest_login, true)
                            .build()
                        findNavController().navigate(R.id.navigate_to_homePage,null, navOptions)
                        activity?.findViewById<BottomNavigationView>(R.id.bottom_nav_view)?.visibility = View.VISIBLE


                    }else {
                        Toast.makeText(context, "Registration Failed", Toast.LENGTH_LONG).show()
                    }
                })
            }
        }
    }
}