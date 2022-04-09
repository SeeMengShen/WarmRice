package com.example.warmrice.Login

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.warmrice.R
import com.example.warmrice.databinding.FragmentForgetPasswordBinding
import com.example.warmrice.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private val nav by lazy {findNavController()}
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        auth = FirebaseAuth.getInstance()

        //Login Button
        binding.loginButton.setOnClickListener { login() }

        //Forget Password Button
         binding.forgetPassword.setOnClickListener { nav.navigate(R.id.forgetPasswordFragment) }

        //Create Account Button
        binding.createAcc.setOnClickListener { nav.navigate(R.id.registerFragment) }

        return binding.root
    }

    private fun login(){
        val email = binding.email.text.toString()
        val password = binding.password.text.toString()
        //val rememberMe = binding.rememberMe
        val user = auth.currentUser

        if(email.isBlank() || password.isBlank()){
            toast("Email and Password can't be blank")
            return
        }

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(){
            if(it.isSuccessful){
                if(auth.currentUser!!.isEmailVerified){
                    toast("Login Successful")
                    nav.navigate(R.id.homeFragment)
                }
                else{
                    toast("Please verify your email address")
                }
            }
            else
                toast("Invalid Login Detail")
        }
    }

    private fun toast(text: String){
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }
}