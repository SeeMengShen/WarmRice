package com.example.warmrice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.warmrice.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val nav by lazy {
        supportFragmentManager.findFragmentById(R.id.nav_host)!!.findNavController()
    }
    private lateinit var bottomNav: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val appBarConfiguration =
            AppBarConfiguration(setOf(R.id.homeFragment, R.id.accountFragment, R.id.aboutUsFragment))
        bottomNav = binding.bottomNav

        setupActionBarWithNavController(nav, appBarConfiguration)
        bottomNav.setupWithNavController(nav)
//        bottomNav.visibility = View.INVISIBLE
    }

    override fun onSupportNavigateUp(): Boolean {
        return nav.navigateUp() || super.onSupportNavigateUp()
    }

//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.top_action_bar, menu)
//        return super.onCreateOptionsMenu(menu)
//    }

    /*
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return item.onNavDestinationSelected(nav) || super.onOptionsItemSelected(item)
    }
    */
}


