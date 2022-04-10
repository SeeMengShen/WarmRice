package com.example.warmrice.data

import android.graphics.drawable.Drawable
import com.example.warmrice.R
import com.google.firebase.firestore.Blob
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.IgnoreExtraProperties
import java.util.*
import kotlin.collections.ArrayList


data class Post(
    @DocumentId
    var postId: String = "",
    var postTitle: String = "",
    var postText: String = "",
    var userEmail: String = "",
    var postDate: Date = Date(),
    var postPhoto: Blob? = null,
    @get: Exclude
    var user: User = User(),
    @get: Exclude
    var comments : List<Comment> = emptyList()

    /*
    var likeCount   : Int,
    var commentCount: Int,
    var favourited  : Boolean
    */
)

data class Comment(
    @DocumentId
    var commentId: String = "",
    var commentText: String = "",
    var userEmail: String = "",
    var commentDate: Date = Date(),

    @get: Exclude
    var user: User = User(),
)

data class User(
    @DocumentId
    var userEmail: String = "",
    var username: String = "",
    var userPhoto: Blob? = null,
//    var userPhoto: Blob? = Blob.fromBytes(ByteArray(0)),
    var userBio: String = ""
)

data class Donation(
    @DocumentId
    var donationId: String = "",
    var donationAmount: Double = 0.0,
    var donationDate: Date = Date(),
    var userEmail: String = "",
    var user: User = User()
)

data class News(
    @DocumentId
    var newsTitle: String = "",
    var newsDate: Date = Date(),
    var newsText: String = "",
    var newsPhoto: Int = R.drawable.icons8_kawaii_rice_90
)
