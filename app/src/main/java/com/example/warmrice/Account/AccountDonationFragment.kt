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
import com.example.warmrice.data.Donation
import com.example.warmrice.data.PostViewModel
import com.example.warmrice.databinding.FragmentAccountDonationBinding
import com.example.warmrice.util.DonationAdapter
import java.util.*

class AccountDonationFragment : Fragment() {

    private lateinit var binding: FragmentAccountDonationBinding
    private val nav by lazy { findNavController() }

    //TODO Create DonationViewModel
//    private val dvm: DonationViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAccountDonationBinding.inflate(inflater, container, false)

        val adapter = DonationAdapter() { holder, post ->
            holder.root.setOnClickListener {
//                nav.navigate(R.id.postDetailsFragment, bundleOf("postId" to post.postId))
            }
        }

        binding.donationRv.adapter = adapter
        binding.donationRv.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

        //Dummy Data
        val donations = listOf(
            Donation("AAA", 100.0, Date()),
            Donation("BBB", 200.0, Date())
        )

        adapter.submitList(donations)

        return binding.root
    }
}