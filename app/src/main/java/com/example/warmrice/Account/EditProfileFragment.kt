package com.example.warmrice.Account

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.warmrice.R
import com.example.warmrice.data.CommentViewModel
import com.example.warmrice.data.User
import com.example.warmrice.data.UserViewModel
import com.example.warmrice.databinding.FragmentEditProfileBinding
import com.example.warmrice.util.cropToBlob
import com.example.warmrice.util.toBitmap
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class EditProfileFragment : Fragment() {

    private lateinit var binding: FragmentEditProfileBinding
    private val nav by lazy{ findNavController() }
    private val uvm: UserViewModel by activityViewModels()

    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                binding.previewImgView.setImageURI((it.data?.data))
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentEditProfileBinding.inflate(inflater, container, false)

        with(binding) {
            selectPhotoBtn.setOnClickListener { selectImg() }
            resetBtn.setOnClickListener{ reset() }
            applyBtn.setOnClickListener{ updateUserInfo() }
            //TODO reset password function
            resetPwdBtn.setOnClickListener{ }
        }

        reset()

        return binding.root
    }

    private fun selectImg() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        launcher.launch(intent)
    }

    private fun reset(){
//        binding.previewImgView.setImageBitmap(user.userPhoto>.toBitmap())
//        binding.editUserame.text = user.username
//        binding.edtBio.text = user.userBio
    }

    //TODO update user to firestore
    private fun updateUserInfo(){
        val firebaseUsers = Firebase.firestore.collection("users").document(/*user.userEmail*/)

        val user = User(
            //TODO set currentUserEmail
            userEmail = "seemengshen@gmail.com",
            username = binding.editUserame.text.toString(),
            userBio = binding.edtBio.text.toString(),
            userPhoto = binding.previewImgView.cropToBlob(100, 100)
        )

        //TODO info validation
        lifecycleScope.launch {
            val err = uvm.validate(user)

            if(err != ""){
                toast(err)
                return@launch
            }

            firebaseUsers.set(user).addOnSuccessListener {
                nav.navigateUp()
                toast("Updated successfully!")
            }
        }
    }

    private fun toast(text: String){
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }
}