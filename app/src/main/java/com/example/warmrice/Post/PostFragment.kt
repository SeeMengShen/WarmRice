package com.example.warmrice.Post

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.warmrice.R
import com.example.warmrice.data.Post
import com.example.warmrice.data.PostViewModel
import com.example.warmrice.databinding.FragmentLoginBinding
import com.example.warmrice.databinding.FragmentPostBinding
import com.example.warmrice.util.cropToBlob
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

class PostFragment: Fragment() {

    private lateinit var binding: FragmentPostBinding
    private lateinit var binding1 : FragmentLoginBinding
    private val nav by lazy { findNavController() }
    private val pvm: PostViewModel by activityViewModels()

    companion object {
        val IMAGE_REQUEST_CODE = 100
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentPostBinding.inflate(inflater, container, false)
        binding1 = FragmentLoginBinding.inflate(inflater, container, false)

        binding.uploadPhoto.setOnClickListener { uploadPhoto() }
        binding.postButton.setOnClickListener { addPost() }

        return binding.root
        return binding1.root
    }


    //UPLOAD PHOTO
    private fun uploadPhoto() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.setType("image/*")
        startActivityForResult(intent, IMAGE_REQUEST_CODE)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            binding.postImageView.setImageURI(data?.data)
        }
    }


    //ADD POST
    private fun addPost() {

        //DETAILS OF USER INPUT
        val addPostDetails = Post(
            postTitle = binding.postTitleInput.getText().toString().trim(),
            postText = binding.postContentInput.getText().toString().trim(),
            postPhoto = binding.postImageView.cropToBlob(300, 300)
        )


        val addPostDatabase = FirebaseFirestore.getInstance()
        val addPostDetail: MutableMap<String, Any> = HashMap()
        addPostDetail["postTitle"] = binding.postTitleInput.getText().toString().trim()
        addPostDetail["postText"] = binding.postContentInput.getText().toString().trim()
        addPostDetail["postPhoto"] = binding.postImageView.cropToBlob(300, 300)
        addPostDetail["postDate"] = Calendar.getInstance().time


        //IF NO ERROR, ADDED TO FIRESTORE WITH THE GENERATED ID OTHERWISE DISPLAY ERROR MESSAGE
        val errorPostSomething = pvm.validatePost(addPostDetails)
        if (errorPostSomething != "")
        {
            toastAddPost(errorPostSomething)
        }
        else {
            addPostDatabase.collection("posts")
                .add(addPostDetail)
                .addOnSuccessListener {
                    toastAddPost(getString(R.string.post_data_added))
                }
           //nav.navigateUp()
        }


    }


    //DISPLAY TOAST IF POST IS ADDED SUCCESSFULLY
    private fun toastAddPost(text: String)
    {
        Toast.makeText(context, text, Toast.LENGTH_LONG).show()

    }

}