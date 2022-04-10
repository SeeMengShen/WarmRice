package com.example.warmrice.Home

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.warmrice.R
import com.example.warmrice.databinding.ActivityMainBinding
import com.example.warmrice.databinding.FragmentHomeBinding
import com.example.warmrice.util.HomeTabAdapter
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val nav by lazy { findNavController() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        val fragments = listOf(
            ForumFragment(),
            NewsFragment()
        )

        val adapter = HomeTabAdapter(fragments, this)
        binding.homeViewPager.adapter = adapter

        TabLayoutMediator(binding.homeTabLayout, binding.homeViewPager){ tab, position ->
         tab.text = when(position){
             0 -> "Forum"
             1 -> "News"
             else -> ""
         }
        }.attach()

        setHasOptionsMenu(true)

//        val mainActivityBinding = ActivityMainBinding.inflate(layoutInflater)
//        mainActivityBinding.bottomNav.visibility = View.VISIBLE

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.home_top_action_bar, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        nav.navigate(R.id.searchFragment)
        return super.onOptionsItemSelected(item)
    }
}