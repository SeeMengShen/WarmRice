package com.example.warmrice.AboutUs

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.warmrice.R
import com.example.warmrice.databinding.FragmentAboutUsBinding
import com.example.warmrice.databinding.FragmentLocationBinding
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment


class LocationFragment : Fragment(){
    private lateinit var binding: FragmentLocationBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View {
        binding = FragmentLocationBinding.inflate(inflater, container, false)

        binding.locationButton.setOnClickListener { openMap() }
        binding.locationImageButton.setOnClickListener{openMap()}

        return binding.root
    }

    private fun openMap(){
        val coordinateUri = Uri.parse("geo:3.2161639231742707, 101.72902357129288")
        val mapIntent = Intent(Intent.ACTION_VIEW, coordinateUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        startActivity(mapIntent)
    }
}