package com.example.warmrice.AboutUs

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.warmrice.databinding.FragmentAboutUsBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class AboutUsFragment : Fragment() {

    private lateinit var binding: FragmentAboutUsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View {
        binding = FragmentAboutUsBinding.inflate(inflater, container, false)

        val fragments = listOf(
            OrganizationFragment(),
            LocationFragment(),
            ContactUsFragment()
        )

        val adapter = AboutUsViewPagerAdapter(fragments, this)
        binding.aboutusViewPager.adapter = adapter

        TabLayoutMediator(binding.aboutusTabLayout, binding.aboutusViewPager){ tab, position ->
            tab.text = when(position){
                0 -> "Organization"
                1 -> "Location"
                2 -> "Contact Us"
                else -> ""
            }
        }.attach()

        return binding.root
    }
}