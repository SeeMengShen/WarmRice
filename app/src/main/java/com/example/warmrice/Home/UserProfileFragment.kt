package com.example.warmrice.Home

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.warmrice.Account.AccountDonationFragment
import com.example.warmrice.Account.AccountFavouritedPostsFragment
import com.example.warmrice.Account.AccountPostsFragment
import com.example.warmrice.data.Post
import com.example.warmrice.data.User
import com.example.warmrice.data.UserSearchViewModel
import com.example.warmrice.data.UserViewModel
import com.example.warmrice.databinding.FragmentUserProfileBinding
import com.example.warmrice.util.AccountTabAdapter
import com.example.warmrice.util.toBitmap
import com.google.android.material.tabs.TabLayoutMediator
import java.text.SimpleDateFormat
import java.util.*

class UserProfileFragment : Fragment() {

    private lateinit var binding: FragmentUserProfileBinding
    private val nav by lazy { findNavController() }
    private val userEmail by lazy{ arguments?.getString("userEmail", "") ?: "" }
    private val uvm: UserViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserProfileBinding.inflate(inflater, container, false)

        val user = uvm.get(userEmail)

        if(user == null){
            toast("Failed to load the post, please try again!")
            nav.navigateUp()
        }
        else{
            load(user)
        }
        val fragments = listOf(
            AccountPostsFragment(),
            AccountFavouritedPostsFragment(),
            AccountDonationFragment()
        )

        val adapter = AccountTabAdapter(fragments, this)
        binding.userProfileViewPager.adapter = adapter

        TabLayoutMediator(binding.userProfileTabLayout, binding.userProfileViewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Posts"
                1 -> "Favourited"
                2 -> "Donation"
                else -> ""
            }
        }.attach()

        return binding.root
    }

    private fun toast(text: String){
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

    private fun load(user: User){
        val formatter = SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault())

        binding.userProfileUserPhotoImageView.setImageBitmap(user.userPhoto?.toBitmap())
        binding.userProfileUsernameView.text = user.username
        binding.userProfileUserEmailView.text = user.userEmail
        //binding.userProfileDateJoinedView.text = formatter.format()
        binding.userProfileBioView.text = user.userBio
    }
}