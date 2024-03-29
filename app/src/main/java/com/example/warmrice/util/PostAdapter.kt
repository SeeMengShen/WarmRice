package com.example.warmrice.util

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.graphics.drawable.toDrawable
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
        val postPhotoView: ImageView = view.findViewById(R.id.postPhotoView)
        val postLikeBtn: Button = view.findViewById(R.id.postLikeBtn)
        val postCommentBtn: Button = view.findViewById(R.id.postCommentBtn)
        val postFavouritedBtn: ImageButton = view.findViewById(R.id.postFavouritedBtn)
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

        if(post.user.userPhoto != null){
            holder.postUserPhotoView.setImageBitmap(post.user.userPhoto?.toBitmap())
        }else{
            holder.postUserPhotoView.setImageResource(R.drawable.icons8_kawaii_rice_90)
        }

        holder.postUsernameView.text = post.user.username
        holder.postDateView.text = formatter.format(post.postDate)
        holder.postContentView.text = post.postText
        holder.postPhotoView.setImageBitmap(post.postPhoto?.toBitmap())
        holder.postCommentBtn.text = post.comments.size.toString()

        fn(holder, post)
    }
}