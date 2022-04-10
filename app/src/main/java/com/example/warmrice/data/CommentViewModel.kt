package com.example.warmrice.data

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.warmrice.Home.PostDetailsFragment
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class CommentViewModel: ViewModel() {

    var comments = MutableLiveData<List<Comment>>()

    fun get(commentId: String): Comment? {
        return comments.value?.find { comment -> comment.commentId == commentId }
    }

    fun getAll() = comments

    fun delete(postId: String, commentId: String) {
        Firebase.firestore.collection("posts").document(postId).collection("comments").document(commentId).delete()
    }

    fun update(postId: String){
        val users = UserViewModel.users
        Firebase.firestore.collection("posts").document(postId).collection("comments").addSnapshotListener { snap, _ ->
            if (snap == null) return@addSnapshotListener

            comments.value = snap.toObjects()

            for (c in comments.value!!) {
                val user = users.value?.find { u -> u.userEmail == c.userEmail } ?: User()
                c.user = user
            }

            PostViewModel.posts.value?.find { post -> post.postId == postId  }?.comments = comments.value!!
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