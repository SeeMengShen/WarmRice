package com.example.warmrice.util

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.warmrice.R
import com.example.warmrice.data.News
import java.text.SimpleDateFormat
import java.util.*

class NewsAdapter(val fn: (ViewHolder, News) -> Unit /*= { _, _ ->}*/ ): ListAdapter<News, NewsAdapter.ViewHolder>(DiffCallBack) {

    companion object DiffCallBack: DiffUtil.ItemCallback<News>() {
        override fun areItemsTheSame(a: News, b: News) = a.newsTitle == b.newsTitle
        override fun areContentsTheSame(a: News, b: News) = a == b
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val root = view
        val newsTitleView: TextView = view.findViewById(R.id.newsTitleView)
        val newsDateView: TextView = view.findViewById(R.id.newsDateView)
        //val newsContentView: TextView = view.findViewById(R.id.newsContentView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.news_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val news = getItem(position)

        val formatter = SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault())

        holder.newsTitleView.text = news.newsTitle
        holder.newsDateView.text = formatter.format(news.newsDate)

        fn(holder, news)
    }
}