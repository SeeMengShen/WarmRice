package com.example.warmrice.Home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import coil.load
import com.example.warmrice.data.Post
import com.example.warmrice.data.PostViewModel
import com.example.warmrice.databinding.FragmentHomeBinding
import com.example.warmrice.databinding.FragmentPostDetailsBinding

class PostDetailsFragment : Fragment() {

    private lateinit var binding: FragmentPostDetailsBinding
    private val nav by lazy{ findNavController() }
    private val postId by lazy{ arguments?.getString("postId", "") ?: "" }
    private val pvm: PostViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentPostDetailsBinding.inflate(inflater, container, false)

        return binding.root
    }

    private fun toast(text: String){
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }
}