package com.example.warmrice.data

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UserSearchViewModel: ViewModel() {

    private var result = MutableLiveData<List<User>>()
    private var searchText = ""

    fun getAll() = result

    private fun updateResult() {
        if(searchText.isEmpty()) {
            result.value = emptyList()
            return
        }

        var users = UserViewModel.users.value

        users = users?.filter { user ->
            (user.userEmail.contains(searchText, true))||
                    user.username.contains(searchText, true)||
                    user.userBio.contains(searchText, true)
        }

        result.value = users!!
    }

    fun search(userInput: String){
        this.searchText = userInput
        updateResult()
    }
}