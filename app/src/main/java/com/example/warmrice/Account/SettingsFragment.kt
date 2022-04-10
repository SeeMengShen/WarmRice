package com.example.warmrice.Account

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.warmrice.R
import com.example.warmrice.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    private lateinit var binding: FragmentSettingsBinding
    private val nav by lazy { findNavController() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)

        with(binding){
            editProfile.setOnClickListener { nav.navigate(R.id.editProfileFragment) }
            changeLanguage.setOnClickListener { nav.navigate(R.id.changeLanguageFragment) }
            logoutBtn.setOnClickListener { /*TODO call logout function*/ }
        }

        return binding.root
    }
}