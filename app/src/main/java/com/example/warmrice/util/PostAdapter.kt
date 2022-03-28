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
import com.example.warmrice.data.Post
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*

class PostAdapter(val fn: (ViewHolder, Post) -> Unit /*= { _, _ ->}*/ ): ListAdapter<Post, PostAdapter.ViewHolder>(DiffCallBack) {

    companion object DiffCallBack: DiffUtil.ItemCallback<Post>() {
        override fun areItemsTheSame(a: Post, b: Post) = a.postId == b.postId
        override fun areContentsTheSame(a: Post, b: Post) = a == b
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val root = view
        val postTitleView: TextView = view.findViewById(R.id.postTitleView)
        val postUserPhotoView: ImageView = view.findViewById(R.id.postUserPhotoView)
        val postUsernameView: TextView = view.findViewById(R.id.postUsernameView)
        val postDateView: TextView = view.findViewById(R.id.postDateView)
        val postContentView: TextView = view.findViewById(R.id.postContentView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.post_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val post = getItem(position)

        val formatter = SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault())

        holder.postTitleView.text = post.postTitle
        holder.postUserPhotoView.setImageBitmap(post.user.userPhoto?.toBitmap())
        holder.postUsernameView.text = post.user.username
        holder.postDateView.text = formatter.format(post.date)
        holder.postContentView.text = post.postText

        fn(holder, post)
    }
}