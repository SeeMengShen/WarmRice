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
import com.example.warmrice.data.Comment
import com.example.warmrice.data.User
import java.text.SimpleDateFormat
import java.util.*


class CommentAdapter(val fn: (CommentAdapter.ViewHolder, Comment) -> Unit /*= { _, _ ->}*/ ) : ListAdapter<Comment, CommentAdapter.ViewHolder>(DiffCallBack) {

    companion object DiffCallBack : DiffUtil.ItemCallback<Comment>() {
        override fun areItemsTheSame(a: Comment, b: Comment) = a.commentId == b.commentId
        override fun areContentsTheSame(a: Comment, b: Comment) = a == b
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val root = view
        val commentUserPhotoView: ImageView = view.findViewById(R.id.commentUserPhotoView)
        val commentUsernameView: TextView = view.findViewById(R.id.commentUsernameView)
        val commentDateView: TextView = view.findViewById(R.id.commentDateView)
        val commentTextView: TextView = view.findViewById(R.id.commentTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.comment_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val comment = getItem(position)

        val formatter = SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault())

        if(comment.user.userPhoto != null){
            holder.commentUserPhotoView.setImageBitmap(comment.user.userPhoto?.toBitmap())
        }else{
            holder.commentUserPhotoView.setImageResource(R.drawable.icons8_kawaii_rice_90)
        }

        holder.commentUsernameView.text = comment.user.username
        holder.commentDateView.text = formatter.format(comment.commentDate)
        holder.commentTextView.text = comment.commentText

        fn(holder, comment)
    }
}