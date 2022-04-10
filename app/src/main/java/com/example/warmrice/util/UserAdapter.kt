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
import com.example.warmrice.data.User

class UserAdapter (val fn: (ViewHolder, User) -> Unit /*= { _, _ ->}*/ ): ListAdapter<User, UserAdapter.ViewHolder>(DiffCallBack) {

    companion object DiffCallBack: DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(a: User, b: User) = a.userEmail == b.userEmail
        override fun areContentsTheSame(a: User, b: User) = a == b
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val root = view
        val userEmailTextView: TextView = view.findViewById(R.id.userEmailTextView)
        val userPhotoView: ImageView = view.findViewById(R.id.userPhotoView)
        val usernameView: TextView = view.findViewById(R.id.usernameTextView)
        val userBioTextView: TextView = view.findViewById(R.id.userBioTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.user_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = getItem(position)

        holder.userEmailTextView.text = user.userEmail

        if(user.userPhoto != null){
            holder.userPhotoView.setImageBitmap(user.userPhoto?.toBitmap())
        }else{
            holder.userPhotoView.setImageResource(R.drawable.icons8_kawaii_rice_90)
        }

        holder.usernameView.text = user.username
        holder.userBioTextView.text = user.userBio

        fn(holder, user)
    }
}