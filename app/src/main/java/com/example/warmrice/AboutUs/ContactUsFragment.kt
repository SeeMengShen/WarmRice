package com.example.warmrice.AboutUs

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.warmrice.R
import com.example.warmrice.databinding.FragmentAboutUsBinding
import com.example.warmrice.databinding.FragmentContactUsBinding

class ContactUsFragment : Fragment() {

    private lateinit var binding: FragmentContactUsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View {
        binding = FragmentContactUsBinding.inflate(inflater, container, false)

        return binding.root
    }
}