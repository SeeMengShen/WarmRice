package com.example.warmrice.AboutUs

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.warmrice.R
import com.example.warmrice.databinding.FragmentLocationBinding
import com.example.warmrice.databinding.FragmentOrganizationBinding

class OrganizationFragment : Fragment() {
    private lateinit var binding: FragmentOrganizationBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View {
        binding = FragmentOrganizationBinding.inflate(inflater, container, false)

        return binding.root
    }
}