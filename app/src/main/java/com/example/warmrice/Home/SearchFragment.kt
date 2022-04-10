package com.example.warmrice.Home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.activityViewModels
import com.example.warmrice.data.PostSearchViewModel
import com.example.warmrice.data.UserSearchViewModel
import com.example.warmrice.databinding.FragmentSearchBinding
import com.example.warmrice.util.SearchTabAdapter
import com.google.android.material.tabs.TabLayoutMediator

class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private val usvm: UserSearchViewModel by activityViewModels()
    private val psvm: PostSearchViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSearchBinding.inflate(inflater, container, false)

        val fragments = listOf(
            SearchPostFragment(),
            SearchUserFragment()
        )

        val adapter = SearchTabAdapter(fragments, this)
        binding.searchViewPager.adapter = adapter

        TabLayoutMediator(binding.searchTabLayout, binding.searchViewPager){ tab, position ->
            tab.text = when(position){
                //TODO add count
                0 -> "Post"
                1 -> "User"
                else -> ""
            }
        }.attach()

        psvm.search("")
        usvm.search("")

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(keyword: String): Boolean = true

            override fun onQueryTextChange(keyword: String): Boolean {
                psvm.search(keyword)
                usvm.search(keyword)
                return true
            }
        })

        return binding.root
    }

}