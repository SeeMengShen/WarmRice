package com.example.warmrice.HelpService

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.warmrice.R
import com.example.warmrice.databinding.FragmentHelpServiceBinding

class HelpServiceFragment : Fragment() {

    private lateinit var binding: FragmentHelpServiceBinding
    private val nav by lazy { findNavController() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentHelpServiceBinding.inflate(inflater, container, false)


        binding.buttonRegistrationForVolunteerOption.setOnClickListener { nav.navigate(R.id.registerVolunteerFragment) }
        binding.buttonDonateOption.setOnClickListener { nav.navigate(R.id.donateFragment) }
        binding.buttonRequestForHelpOption.setOnClickListener { nav.navigate(R.id.requestHelpFragment) }

        return binding.root

    }

}