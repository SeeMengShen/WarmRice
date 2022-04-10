package com.example.warmrice.Login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.warmrice.R
import com.example.warmrice.databinding.ActivityMainBinding
import com.example.warmrice.databinding.FragmentForgetPasswordBinding
import com.example.warmrice.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private val nav by lazy {findNavController()}

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)

        //Forget Password Button
         binding.forgetPassword.setOnClickListener { nav.navigate(R.id.forgetPasswordFragment) }

        //Create Account Button
        binding.createAcc.setOnClickListener { nav.navigate(R.id.registerFragment) }

        return binding.root
    }
}