package com.example.warmrice.data

import androidx.lifecycle.ViewModel

class NewsViewModel : ViewModel() {

    val news = listOf<News>(
        News(),
        News(),
        News(),
        News(),
        News(),
        News(),
        News(),
        News()
    )

    fun get(newsTitle: String): News? {
        return news.find { news -> news.newsTitle == newsTitle }
    }

    fun getAll() = news
}