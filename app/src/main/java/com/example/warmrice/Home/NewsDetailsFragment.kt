package com.example.warmrice.Home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.warmrice.data.News
import com.example.warmrice.data.NewsViewModel
import com.example.warmrice.data.Post
import com.example.warmrice.data.PostViewModel
import com.example.warmrice.databinding.FragmentNewsDetailsBinding
import com.example.warmrice.util.toBitmap
import java.text.SimpleDateFormat
import java.util.*

class NewsDetailsFragment : Fragment() {

    private lateinit var binding: FragmentNewsDetailsBinding
    private val nav by lazy{ findNavController() }
    private val newsTitle by lazy{ arguments?.getString("newsTitle", "") ?: "" }
    private val nvm: NewsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentNewsDetailsBinding.inflate(inflater, container, false)

        val news = nvm.get(newsTitle)

        if(news == null){
            toast("Failed to load the news, please try again!")
            nav.navigateUp()
        }
        else{
            load(news)
        }

        return binding.root
    }

    private fun toast(text: String){
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

    private fun load(news: News){
        val formatter = SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault())

        binding.newsDetailsNewsTitleView.text = news.newsTitle
        binding.newsDetailsPhotoView.setImageResource(news.newsPhoto)
        binding.newsDetailsNewsDateView.text = formatter.format(news.newsDate)
        binding.newsDetailsNewsTextView.text = news.newsText
    }

}