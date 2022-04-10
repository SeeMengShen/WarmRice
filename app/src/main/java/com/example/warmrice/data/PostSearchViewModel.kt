package com.example.warmrice.data

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class PostSearchViewModel : ViewModel() {

    private var result = MutableLiveData<List<Post>>()
    private var searchText = ""

    fun getAll() = result

    private fun updateResult() {
        if(searchText.isEmpty()) {
            result.value = emptyList()
            return
        }

        var posts = PostViewModel.posts.value

        posts = posts?.filter { post ->
            post.postTitle.contains(searchText, true) ||
                    post.user.username.contains(searchText, true) ||
                    post.postText.contains(searchText, true)
        }

        result.value = posts!!
    }

    fun search(userInput: String) {
        this.searchText = userInput
        updateResult()
    }
}