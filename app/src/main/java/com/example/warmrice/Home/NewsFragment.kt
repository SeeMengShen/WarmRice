package com.example.warmrice.Home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.warmrice.R
import com.example.warmrice.data.News
import com.example.warmrice.data.NewsViewModel
import com.example.warmrice.data.UserViewModel
import com.example.warmrice.databinding.FragmentNewsBinding
import com.example.warmrice.util.NewsAdapter

class NewsFragment : Fragment() {

    private lateinit var binding: FragmentNewsBinding
    private val nav by lazy { findNavController() }
    private val nvm: NewsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewsBinding.inflate(inflater, container, false)

        val adapter = NewsAdapter(){ holder, news, ->
            holder.root.setOnClickListener {
                nav.navigate(R.id.newsDetailsFragment, bundleOf("newsTitle" to news.newsTitle))
            }
        }

        binding.newsRv.adapter = adapter
        binding.newsRv.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

        adapter.submitList(nvm.news)

        return binding.root
    }

}