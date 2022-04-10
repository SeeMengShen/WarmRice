package com.example.warmrice.data

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class UserViewModel : ViewModel() {

    private val firebaseUsers = Firebase.firestore.collection("users")

    companion object{
        var users = MutableLiveData<List<User>>()
    }

    init {
        firebaseUsers.addSnapshotListener() { snap, _ ->
            users.value = snap?.toObjects()
            PostViewModel.updateUser()
        }
    }

    fun get(userEmail: String): User?{
        return users.value?.find { user -> user.userEmail == userEmail }
    }

    fun getAll() = users

    //TODO come back afterwards
    fun delete(userEmail: String){
        firebaseUsers.document(userEmail).delete()
    }

    //====================================Validation==============================================

    suspend fun nameExists(name: String): Boolean{
        return firebaseUsers.whereEqualTo("username", name).get().await().size() > 0
    }

    suspend fun validate(u: User): String{

        return when {
            u.username == "" -> "Don't leave your name blank!"
            nameExists(u.username) -> "Username is not available."
            else -> ""
        }


    }

}