package com.example.warmrice.Login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.warmrice.R
import com.example.warmrice.databinding.ActivityLoginBinding
import com.example.warmrice.databinding.ActivityMainBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val nav by lazy {supportFragmentManager.findFragmentById(R.id.loginHost)!!.findNavController()}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Setup Action Bar
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.loginFragment))
        setupActionBarWithNavController(nav, appBarConfiguration)
    }

    override fun onSupportNavigateUp(): Boolean {
        return nav.navigateUp() || super.onSupportNavigateUp()
    }
}