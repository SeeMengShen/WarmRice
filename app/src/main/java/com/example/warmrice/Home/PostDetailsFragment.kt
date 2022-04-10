package com.example.warmrice.Home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.warmrice.R
import com.example.warmrice.data.Comment
import com.example.warmrice.data.CommentViewModel
import com.example.warmrice.data.Post
import com.example.warmrice.data.PostViewModel
import com.example.warmrice.databinding.ActivityMainBinding
import com.example.warmrice.databinding.FragmentPostDetailsBinding
import com.example.warmrice.util.CommentAdapter
import com.example.warmrice.util.hideKeyboard
import com.example.warmrice.util.openKeyboard
import com.example.warmrice.util.toBitmap
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.android.awaitFrame
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class PostDetailsFragment : Fragment() {

    private lateinit var binding: FragmentPostDetailsBinding
    private val nav by lazy { findNavController() }
    private val postId by lazy { arguments?.getString("postId", "") ?: "" }
    private val goToComment by lazy { arguments?.getBoolean("goToComment", false) ?: false }
    private val pvm: PostViewModel by activityViewModels()
    private val cvm: CommentViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPostDetailsBinding.inflate(inflater, container, false)

        val post = pvm.get(postId)

        if (post == null) {
            toast("Failed to load the post, please try again!")
            nav.navigateUp()
        } else {
            load(post)

            with(binding) {
                postDetailsUsernameView.setOnClickListener { navUserProfileFrag(post) }
                postDetailsUserPhotoView.setOnClickListener { navUserProfileFrag(post) }
                sendCommentBtn.setOnClickListener { sendComment() }
                postDetailsCommentBtn.setOnClickListener { startComment() }
            }
        }

        if (goToComment) {
            startComment()
        }

        val adapter = CommentAdapter() { holder, comment ->
            holder.commentUsernameView.setOnClickListener {
                //TODO: if same with own id then go to account instead of viewAccountFragment
                navUserProfileFrag(comment)
            }
            holder.commentUserPhotoView.setOnClickListener {
                navUserProfileFrag(comment)
            }
        }

        binding.commentsRv.adapter = adapter
        binding.commentsRv.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )

        lifecycleScope.launch {
            cvm.update(postId)
        }

        cvm.getAll().observe(viewLifecycleOwner) { comments ->
            adapter.submitList(comments)
            binding.postDetailsCommentBtn.text = comments.size.toString()
        }

        return binding.root
    }

    private fun toast(text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

    private fun load(post: Post) {
        val formatter = SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault())

        binding.postDetailsTitleView.text = post.postTitle

        if (post.user.userPhoto != null) {
            binding.postDetailsUserPhotoView.setImageBitmap(post.user.userPhoto?.toBitmap())
        } else {
            binding.postDetailsUserPhotoView.setImageResource(R.drawable.icons8_kawaii_rice_90)
        }

        binding.postDetailsUsernameView.text = post.user.username
        binding.postDetailsDateView.text = formatter.format(post.postDate)
        binding.postDetailsContentView.text = post.postText
        binding.postDetailsPhotoView.setImageBitmap(post.postPhoto?.toBitmap())
    }

    private fun navUserProfileFrag(post: Post) {
        nav.navigate(R.id.userProfileFragment, bundleOf("userEmail" to post.user.userEmail))
    }

    private fun navUserProfileFrag(comment: Comment) {
        nav.navigate(R.id.userProfileFragment, bundleOf("userEmail" to comment.user.userEmail))
    }

    private fun sendComment() {
        val firebaseComment =
            Firebase.firestore.collection("posts").document(postId).collection("comments")
                .document()

        val c = Comment(
            commentId = firebaseComment.id,
            commentText = binding.commentEdtTextView.text.toString(),
            commentDate = Date(),

            //TODO change to currentUserEmail
            userEmail = "seemengshen@gmail.com"
        )

        if (c.commentText == "") {
            toast("Type something!")
            return
        }

        firebaseComment.set(c).addOnSuccessListener { commentSuccess() }
    }

    private fun commentSuccess() {
        with(binding) {
            commentEdtTextView.text?.clear()
            commentEdtTextView.clearFocus()
        }
        hideKeyboard()
        toast("Done comment!")
    }

    private fun startComment() {
        with(binding) {
            commentEdtTextView.requestFocus()
            commentEdtTextView.openKeyboard()
        }
    }
}