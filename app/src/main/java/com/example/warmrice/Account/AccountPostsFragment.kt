package com.example.warmrice.Account

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
import com.example.warmrice.data.PostViewModel
import com.example.warmrice.data.UserViewModel
import com.example.warmrice.databinding.FragmentAccountPostsBinding
import com.example.warmrice.util.PostAdapter

class AccountPostFragment : Fragment() {

    private lateinit var binding: FragmentAccountPostsBinding
    private val nav by lazy { findNavController() }
    private val pvm: PostViewModel by activityViewModels()
    private val uvm: UserViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAccountPostsBinding.inflate(inflater, container, false)

        val adapter = PostAdapter() { holder, post ->
            holder.root.setOnClickListener {
                nav.navigate(R.id.postDetailsFragment, bundleOf("postId" to post.postId))
            }
            holder.postUsernameView.setOnClickListener {
                //TODO: if same with own id then go to account instead of viewAccountFragment
                nav.navigate(R.id.accountFragment, bundleOf("userId" to post.user.userEmail))
            }
        }

        binding.accountPostsRv.adapter = adapter
        binding.accountPostsRv.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

        uvm.getAll().observe(viewLifecycleOwner) { posts ->
        }

        pvm.getAll().observe(viewLifecycleOwner){ posts ->
            pvm.assignUser(posts)
            adapter.submitList(posts)
        }

        return binding.root
    }

}