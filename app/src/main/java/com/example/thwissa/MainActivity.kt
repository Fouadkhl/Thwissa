package com.example.thwissa

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.thwissa.databinding.ActivityMainBinding
import com.example.thwissa.fragment.ProfileFragment
import com.example.thwissa.fragment.discuss.DiscussFragment
import com.example.thwissa.fragment.entertainment.EntertainmentFragment
import com.example.thwissa.fragment.homefragment.HomeFragment
import com.example.thwissa.fragment.newsfragment.fragments.NewsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.security.MessageDigest


@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        this.getWindow().setStatusBarColor(this.getColor(R.color.profile_color))


        val bottomNavigationView= findViewById<BottomNavigationView>(R.id.bottom_nav_view)
        val newsNavHost= findViewById<View>(R.id.nav_host_fragment_container)
        bottomNavigationView.setupWithNavController(newsNavHost.findNavController())

        // TODO: authenticate the user when the onstart launched

//        bottomNavigationView.setOnNavigationItemReselectedListener {
//            when (it.itemId) {
//                R.id.homeFragment -> {
//                    loadFragment(HomeFragment())
//                    return@setOnNavigationItemReselectedListener
//                }
//                R.id.newsFragment -> {
//                    loadFragment(NewsFragment())
//                    return@setOnNavigationItemReselectedListener
//                }
//                R.id.discussFragment -> {
//                    loadFragment(DiscussFragment())
//                    return@setOnNavigationItemReselectedListener
//                }
//                R.id.entertainmentFragment -> {
//                    loadFragment(EntertainmentFragment())
//                    return@setOnNavigationItemReselectedListener
//                }
//
//                R.id.profileFragment -> {
//                    loadFragment(ProfileFragment())
//                    return@setOnNavigationItemReselectedListener
//                }
//            }
//        }

    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}