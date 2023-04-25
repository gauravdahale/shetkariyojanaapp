package com.gtech.yojana

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.firebase.auth.FirebaseAuth
import com.gtech.yojana.databinding.ActivityMainBinding
import com.gtech.yojana.ui.login.LoginActivity
import kotlin.math.log

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding
    lateinit var mAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView
        mAuth = FirebaseAuth.getInstance()
        navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_dashboard,
                R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            if (destination.id == R.id.navigation_home) {
                Log.d(TAG, "onCreate: ")
            }

//            Toast.makeText(applicationContext, destination.label, Toast.LENGTH_SHORT).show()
            when (destination.id) {
                R.id.navigation_home -> {

                }
            }
        }
    try {
        checkLogin()
    }catch (e:java.lang.Exception){
        Log.d(TAG, "onCreate() returned: ${e.message}")
    }

    }

    private fun checkLogin() {
        if (mAuth.currentUser?.uid == null) {
            val i = Intent(this, LoginActivity::class.java)
            startActivity(i)
            finish()
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}