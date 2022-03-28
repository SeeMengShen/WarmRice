package com.example.warmrice.data

import android.app.Application
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

    private val firebasePosts = Firebase.firestore.collection("posts")

    companion object {
        var posts = MutableLiveData<List<Post>>()
    }

    init {
        firebasePosts.addSnapshotListener { snap, _ ->
            posts.value = snap?.toObjects()
        }
    }

    suspend fun get(postId: String): Post? {
        return firebasePosts.document(postId).get().await().toObject<Post>()
    }

    fun getAll() = posts

    fun set(post: Post) {
        firebasePosts.document(post.postId).set(post)
    }

    fun delete(postId: String) {
        firebasePosts.document(postId).delete()
    }

    fun assignUser(posts: List<Post>) {
        posts.forEach { post ->
            post.user = UserViewModel.users.value?.find { user ->
                user.userEmail == post.userEmail
            }!!
        }
    }
}




