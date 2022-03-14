package com.example.warmrice.Home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.warmrice.databinding.FragmentHomeBinding
import com.example.warmrice.databinding.FragmentPostDetailsBinding

class PostDetailsFragment : Fragment() {

    private lateinit var binding: FragmentPostDetailsBinding
    private val nav by lazy{ findNavController() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentPostDetailsBinding.inflate(inflater, container, false)

        return binding.root
    }

}