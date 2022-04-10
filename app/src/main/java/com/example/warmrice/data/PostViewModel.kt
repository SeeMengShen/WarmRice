package com.example.warmrice.data

import android.app.Application
import android.os.Debug
import android.util.Log
import android.util.LogPrinter
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.net.URL

class PostViewModel : ViewModel() {

    companion object {
        private val firebasePosts = Firebase.firestore.collection("posts")

        var posts = MutableLiveData<List<Post>>()

        fun updateUser(){
            val users = UserViewModel.users
            firebasePosts.addSnapshotListener { snap, _ ->
                if (snap == null) return@addSnapshotListener

                posts.value = snap.toObjects()

                for (p in posts.value!!) {
                    val user = users.value?.find { u -> u.userEmail == p.userEmail } ?: User()
                    p.user = user
                }
            }
        }
    }

    init {
//        firebasePosts.addSnapshotListener { snap, _ ->
//            posts.value = snap?.toObjects()
//        }

        viewModelScope.launch {
            updateUser()
        }
    }

    fun get(postId: String): Post? {
        return posts.value?.find { post -> post.postId == postId }
    }

    fun getAll() = posts

    fun set(post: Post) {
        firebasePosts.document(post.postId).set(post)
    }

    fun delete(postId: String) {
        firebasePosts.document(postId).delete()
    }

    //VALIDATION OF POST DETAILS
    fun validatePost(post: Post, insert: Boolean = true): String {
        var errorAddPost = ""

        errorAddPost += if (post.postTitle.isNullOrEmpty()) "Invalid. Title is required.\n"
        else ""

        errorAddPost += if (post.postPhoto.toBytes().isEmpty()) "Invalid. Photo is required.\n"
        else ""

        return errorAddPost
    }
}

//    fun assignUser(posts: List<Post>) {
//        posts.forEach { post ->
//            post.user = UserViewModel.users.value?.find { user ->
//                user.userEmail == post.userEmail
//            }!!
//        }
//    }
}