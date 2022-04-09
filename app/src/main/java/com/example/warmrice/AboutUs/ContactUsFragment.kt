package com.example.warmrice.AboutUs

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.warmrice.R
import com.example.warmrice.databinding.FragmentAboutUsBinding
import com.example.warmrice.databinding.FragmentContactUsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ContactUsFragment : Fragment() {

    private lateinit var binding: FragmentContactUsBinding
    private lateinit var auth: FirebaseAuth

    private val firebaseContactUs = Firebase.firestore.collection("contactUs")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View {
        binding = FragmentContactUsBinding.inflate(inflater, container, false)
        auth = FirebaseAuth.getInstance()

        binding.submitButton.setOnClickListener { contactUsMessage() }

        binding.facebookButton.setOnClickListener{
            val openFaceboook = Intent(Intent.ACTION_VIEW)
            openFaceboook.data = Uri.parse("https://www.facebook.com/tunkuabdulrahmanuniversitycollege/")
            startActivity(openFaceboook)
        }

        binding.instagramButton.setOnClickListener{
            val openInstagram = Intent(Intent.ACTION_VIEW)
            openInstagram.data = Uri.parse("https://www.instagram.com/taruc_official/")
            startActivity(openInstagram)
        }

        binding.websiteButton.setOnClickListener{
            val openWebsite = Intent(Intent.ACTION_VIEW)
            openWebsite.data = Uri.parse("https://www.tarc.edu.my/")
            startActivity(openWebsite)
        }

        binding.contactUsOpenemail.setOnClickListener{
            val openEmail = Intent(Intent.ACTION_SENDTO)
            openEmail.setData(Uri.parse("mailto:warmrice@gmail.com"))
            //openEmail.type = "message/rfc822"
            //startActivity(Intent.createChooser(openEmail, "Select Email"))
            startActivity(openEmail)
        }

        binding.contactUsOpenphonenum.setOnClickListener {
            val openPhone = Intent(Intent.ACTION_DIAL)
            openPhone.setData(Uri.parse("tel:0123456789"))
            startActivity(openPhone)
        }

        return binding.root
    }

    private fun contactUsMessage() {
        val name = binding.contactUsName.text.toString()
        val email = binding.contactUsEmail.text.toString()
        val message = binding.contactUsMsg.text.toString()

        if(name.isBlank() || email.isBlank() || message.isBlank()){
            toast("Name, email or message field cannot be empty.")
            return
        }else{
            toast("Request has been sent. Thank you.")
            writeContactUsMessage(name, email, message)
            reset()
        }

    }

    private fun writeContactUsMessage(name: String, email: String, message: String) {
        val newContactUsMessage = hashMapOf(
            "cuName" to name,
            "cuEmail" to email,
            "cuMessage" to message,
        )

        //TODO change document(name) to id
        firebaseContactUs.document(name)
            .set(newContactUsMessage)
    }

    private fun reset(){
        binding.contactUsEmail.text.clear()
        binding.contactUsName.text.clear()
        binding.contactUsMsg.text.clear()
    }

    private fun toast(text: String){
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }
}

