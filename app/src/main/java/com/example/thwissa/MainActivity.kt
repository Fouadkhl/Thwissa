package com.example.thwissa

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.thwissa.databinding.ActivityMainBinding
import com.example.thwissa.fragment.Profile.agencyProfile.AgencyProfileFragment
import com.example.thwissa.fragment.ProfileFragment


@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        this.getWindow().setStatusBarColor(this.getColor(R.color.profile_color))
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
//        window.decorView.apply {
//            systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN
//        }

        val bottomNavigationView = binding.bottomNavView
        val navhost = findViewById<View>(R.id.nav_host_fragment_container)

        bottomNavigationView.setOnItemSelectedListener { item ->
            // In order to get the expected behavior, you have to call default Navigation method manually
            NavigationUI.onNavDestinationSelected(item, navhost.findNavController())
            return@setOnItemSelectedListener true
        }

        // TODO: authenticate the user when the onstart launched
    }


}