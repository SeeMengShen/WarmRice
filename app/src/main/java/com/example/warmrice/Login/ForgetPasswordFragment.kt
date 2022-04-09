package com.example.warmrice.Login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.warmrice.databinding.FragmentForgetPasswordBinding
import com.google.firebase.auth.FirebaseAuth

class ForgetPasswordFragment : Fragment() {

    private lateinit var binding: FragmentForgetPasswordBinding
    private val nav by lazy { findNavController() }
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentForgetPasswordBinding.inflate(inflater, container, false)
        auth = FirebaseAuth.getInstance()

        //Return to Login
        binding.resetPWButton.setOnClickListener {
            forgetPassword()
            //nav.navigateUp()
        }

        return binding.root
    }

    private fun forgetPassword() {
        val email = binding.FPEmail.text.toString()

        if(email.isBlank()){
            toast("Email cannot be blank")
            return
        }

        auth.sendPasswordResetEmail(email).addOnCompleteListener{ task ->
            if(task.isSuccessful){
                toast("A password reset request has been send to your email.")
                nav.navigateUp()
            }
        }
    }

    private fun toast(text: String){
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }
}