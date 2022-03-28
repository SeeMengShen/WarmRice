package com.example.warmrice.data

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
        }
    }

    suspend fun get(userEmail: String): User?{
        return firebaseUsers.document(userEmail).get().await().toObject<User>()
    }

    fun getAll() = users

    fun set(user: User){
        firebaseUsers.document(user.userEmail).set(user)
    }

    //TODO come back afterwards
    fun delete(userEmail: String){
        firebaseUsers.document(userEmail).delete()
    }
}