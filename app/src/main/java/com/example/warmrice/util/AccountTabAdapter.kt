package com.example.warmrice.util

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class AccountTabAdapter(val items: List<Fragment>, fragment: Fragment): FragmentStateAdapter(fragment)
{
    override fun getItemCount(): Int {
        return items.size
    }

    override fun createFragment(position: Int): Fragment {
        return items[position]
    }
}