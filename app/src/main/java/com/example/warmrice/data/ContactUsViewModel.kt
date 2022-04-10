package com.example.warmrice.data

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase

class ContactUsViewModel : ViewModel() {

    private val firebaseContactUs = Firebase.firestore.collection("contactUs")
    private val contactUs = MutableLiveData<List<ContactUs>>()

    init{
        firebaseContactUs.addSnapshotListener { snap, _ ->
            contactUs.value = snap?.toObjects()
        }
    }

    fun get(id: String): ContactUs? {


        return null
    }

    fun getAll() = contactUs

    fun set(cu: ContactUs){

    }

}