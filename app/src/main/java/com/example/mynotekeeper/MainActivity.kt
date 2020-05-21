package com.example.mynotekeeper

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.preference.PreferenceManager
import com.example.mynotekeeper.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var sharedPreferences: SharedPreferences
lateinit var binding: ActivityMainBinding
    lateinit var drawerLayout: DrawerLayout
    lateinit var navigationView:NavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        sharedPreferences=PreferenceManager.getDefaultSharedPreferences(this)

         binding=DataBindingUtil.setContentView<ActivityMainBinding>(this,R.layout.activity_main)
        drawerLayout=binding.navigationDrawer
         navigationView=binding.navigationView
        val navController=findNavController(R.id.fragment)

        NavigationUI.setupActionBarWithNavController(this,navController,drawerLayout)
        NavigationUI.setupWithNavController(navigationView,navController)

        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            if (controller.graph.startDestination==destination.id){
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
            }
            else{
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            }
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController=findNavController(R.id.fragment)
        return  NavigationUI.navigateUp(navController,drawerLayout) ||super.onSupportNavigateUp()
    }

    override fun onResume() {
        super.onResume()
        val name=sharedPreferences.getString("edit_preference_name","Your name")
        val email=sharedPreferences.getString("edit_preference_email","Your email")
        setUpNavigationViewHeader(name,email)
    }

    private fun setUpNavigationViewHeader(name: String?, email: String?) {
        val navHeader=navigationView.getHeaderView(0)
        name?.let {
            navHeader.findViewById<TextView>(R.id.tv_your_name).text=it
        }
        email?.let {
            navHeader.findViewById<TextView>(R.id.tv_your_email).text=it
        }
    }


}
