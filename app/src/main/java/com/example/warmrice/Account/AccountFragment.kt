package com.example.warmrice.Account

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.example.warmrice.R
import com.example.warmrice.databinding.FragmentAccountBinding
import com.example.warmrice.util.AccountTabAdapter
import com.google.android.material.tabs.TabLayoutMediator

class AccountFragment : Fragment() {

    private lateinit var binding: FragmentAccountBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAccountBinding.inflate(inflater, container, false)

        val fragments = listOf(
            AccountPostFragment(),
            AccountFavouritedPostsFragment(),
            AccountDonationFragment()
        )

        val adapter = AccountTabAdapter(fragments, this)
        binding.accountViewPager.adapter = adapter

        TabLayoutMediator(binding.accountTabLayout, binding.accountViewPager){ tab, position ->
            tab.text = when(position){
                0 -> "Posts"
                1 -> "Favourited"
                2 -> "Donation"
                else -> ""
            }
        }.attach()

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.account_top_action_bar, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
}