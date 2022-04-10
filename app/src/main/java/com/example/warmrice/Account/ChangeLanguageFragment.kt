package com.example.warmrice.Account

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.warmrice.R
import com.example.warmrice.databinding.FragmentChangeLanguageBinding

class ChangeLanguageFragment : Fragment() {

    private lateinit var binding: FragmentChangeLanguageBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentChangeLanguageBinding.inflate(inflater, container, false)

        return binding.root
    }
}