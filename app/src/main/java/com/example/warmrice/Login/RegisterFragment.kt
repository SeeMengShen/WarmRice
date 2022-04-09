package com.example.warmrice.Login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.warmrice.R
import com.example.warmrice.databinding.FragmentRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding
    private val nav by lazy { findNavController() }
    private lateinit var auth: FirebaseAuth

    private val firebaseUser = Firebase.firestore.collection("users")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        auth = FirebaseAuth.getInstance()
        //Return to Login
        binding.createButton.setOnClickListener { register() }

        return binding.root
    }

    private fun register(){
        val email = binding.regEmail.text.toString()
        val password = binding.regPassword.text.toString()
        val confirmPassword = binding.regPasswordConfirm.text.toString()
        val userName = "Unknown"
        val userBio = "Empty Bio"

        //TODO: Change to the kawaii rice photo
        //val userPhoto = R.drawable.icon2_0

        if(email.isBlank() || password.isBlank() || confirmPassword.isBlank()){
            toast("Email and Password can't be blank")
            return
        }

        if(password != confirmPassword){
            toast("Password and Confirm Password do not match")
            return
        }

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            val user = auth.currentUser

            if(task.isComplete){
                user!!.sendEmailVerification().addOnCompleteListener {
                    if(task.isSuccessful){
                        toast("Registered successfully, please check your email address for verification.")
                        writeNewUser(email, userName, userBio)
                        nav.navigateUp()
                    }
                    else{
                        Toast.makeText(context, task.exception!!.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun writeNewUser(email: String, userName: String, userBio: String) {
        val newUser = hashMapOf(
            "userBio" to userBio,
            "username" to userName,
            "userphoto" to null
        )

        firebaseUser.document(email)
            .set(newUser)
    }

    private fun toast(text: String){
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }
}
