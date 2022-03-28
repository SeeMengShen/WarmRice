package com.example.warmrice.util

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.warmrice.R
import com.example.warmrice.data.Donation
import java.text.SimpleDateFormat
import java.util.*

class DonationAdapter(val fn: (ViewHolder, Donation) -> Unit /*= { _, _ ->}*/ ): ListAdapter<Donation, DonationAdapter.ViewHolder>(DiffCallBack) {

    companion object DiffCallBack: DiffUtil.ItemCallback<Donation>() {
        override fun areItemsTheSame(a: Donation, b: Donation) = a.donationId == b.donationId
        override fun areContentsTheSame(a: Donation, b: Donation) = a == b
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val root = view
        val donationAmountView: TextView = view.findViewById(R.id.donationAmountView)
        val donationDateView: TextView = view.findViewById(R.id.donationDateView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.donation_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val donation = getItem(position)

        val formatter = SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault())

        holder.donationAmountView.text = donation.donationAmount.toString()
        holder.donationDateView.text = formatter.format(donation.donationDate)

        fn(holder, donation)
    }
}