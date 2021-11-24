package com.stocktracking.app

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomnavigation.BottomNavigationView.OnNavigationItemReselectedListener
import com.google.firebase.auth.FirebaseAuth
import com.stocktracking.app.home.HomePageFragment


class MainActivity : AppCompatActivity() {

    lateinit var host: NavHostFragment
    private val homePageFragment: Fragment = HomePageFragment()
    private var auth = FirebaseAuth.getInstance()
    private var bottomNavigationView: BottomNavigationView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottomNavigationView = findViewById(R.id.bottom_nav_view)
        host = supportFragmentManager
            .findFragmentById(R.id.my_nav_host_fragment) as NavHostFragment? ?: return
        val navController = host.navController
        if(auth.currentUser != null) {
            findViewById<BottomNavigationView>(R.id.bottom_nav_view)?.visibility = View.VISIBLE
            bottomNavigationView!!.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.dest_homePage -> {
                        return@OnNavigationItemSelectedListener true
                    }
                    R.id.dest_wishlist -> {
                        return@OnNavigationItemSelectedListener true
                    }
                    R.id.dest_userProfile -> {
                        return@OnNavigationItemSelectedListener true
                    }
                    else -> {
                        return@OnNavigationItemSelectedListener true
                    }
                }

            })

            bottomNavigationView!!.setOnNavigationItemReselectedListener(
                OnNavigationItemReselectedListener {

                })

            setupBottomNavMenu(navController)

            val navOptions = NavOptions.Builder()
                .setPopUpTo(R.id.dest_login, true)
                .build()
            host.navController.navigate(R.id.navigate_to_homePage, null, navOptions)

        }

    }


    private fun setupBottomNavMenu(navController: NavController) {
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_nav_view)
        bottomNav?.setupWithNavController(navController)
    }
}