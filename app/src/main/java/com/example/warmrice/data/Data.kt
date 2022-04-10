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
    var donationCreditOrDebitCardNumberInput : String = "",
    var donationCCVInput : String = "",
    var donationAmountDonatedInput : String = "",
    var donationLeaveComment : String = ""
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

//REGISTER FOR VOLUNTEER DATA
data class RegisterForVolunteer(
    @DocumentId
    var registerForVolunteerNameInput : String = "",
    var registerForVolunteerIdentificationNumberInput : String = "",
    var registerForVolunteerEmailAddressInput : String = "",
    var registerForVolunteerPhoneNumberInput : String = "",
    var registerForVolunteerAddressInput : String = "",
    var registerForVolunteerStateInput : String = "",
    var registerForVolunteerPostalOrZipCodeInput : String = "",
    var registerForVolunteerReasonToVolunteer : String = "",
    var registerForVolunteerDate : Date = Date()
)


//REQUEST FOR HELP DATA
data class RequestForHelp(
    @DocumentId
    var requestForHelpNameInput : String = "",
    var requestForHelpIdentificationNumberInput : String = "",
    var requestForHelpEmailAddressInput : String = "",
    var requestForHelpPhoneNumberInput : String = "",
    var requestForHelpAddressInput : String = "",
    var requestForHelpStateInput : String = "",
    var requestForHelpPostalOrZipCodeInput : String = "",
    var requestForHelpTypeOfHelpRequired : Int = 0,
    var requestForHelpDate : Date = Date()
)

data class ContactUs(
    @DocumentId
    var name: String = "",
    var email: String = "",
    var message: String = ""
)
