package com.example.warmrice.Home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.children
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.warmrice.R
import com.example.warmrice.data.UserSearchViewModel
import com.example.warmrice.data.UserViewModel
import com.example.warmrice.databinding.FragmentSearchUserBinding
import com.example.warmrice.util.UserAdapter
import com.google.android.material.tabs.TabItem
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class SearchUserFragment : Fragment() {

    private lateinit var binding: FragmentSearchUserBinding
    private val nav by lazy { findNavController() }
    private val usvm: UserSearchViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSearchUserBinding.inflate(inflater, container, false)

        val adapter = UserAdapter() { holder, user ->
            holder.root.setOnClickListener {
                nav.navigate(R.id.userProfileFragment, bundleOf("userEmail" to user.userEmail))
            }
        }

        binding.searchUserRv.adapter = adapter
        binding.searchUserRv.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

        //TODO searching
        usvm.getAll().observe(viewLifecycleOwner){ users ->
//            pvm.assignUser(posts)
            adapter.submitList(users)
            binding.searchUserResultCountView.text = "${users.size} record(s)"
        }

        return binding.root
    }

}