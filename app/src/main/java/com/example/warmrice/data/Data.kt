package com.example.warmrice.data

import com.google.firebase.firestore.Blob
import com.google.firebase.firestore.DocumentId
import java.util.*


data class Post(
    @DocumentId
    var postId: String = "",
    var postTitle: String = "",
    var postText: String = "",
    var userEmail: String = "",
    var user: User = User(),
    var date: Date = Date()
    /*
    var postImageUrl: String,
    var likeCount   : Int,
    var commentCount: Int,
    var favourited  : Boolean
    */
)

data class User(
    @DocumentId
    var userEmail: String = "",
    var username: String = "",
    var userPhoto: Blob? = Blob.fromBytes(ByteArray(0)),
    var userBio: String = ""
)

data class Donation(
    @DocumentId
    var donationId: String = "",
    var donationAmount: Double = 0.0,
    var donationDate: Date = Date()
)
