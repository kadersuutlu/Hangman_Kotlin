package com.kader.kotlin_hangman.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kader.kotlin_hangman.R
import com.kader.kotlin_hangman.databinding.ItemHeartBinding

class HeartAdapter(private var heartCount: Int) :
    RecyclerView.Adapter<HeartAdapter.HeartViewHolder>() {

    private var remainingAttempts: Int = heartCount

    inner class HeartViewHolder(binding: ItemHeartBinding) : RecyclerView.ViewHolder(binding.root) {
        val heart = binding.heart1
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HeartAdapter.HeartViewHolder {
        val binding = ItemHeartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HeartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HeartAdapter.HeartViewHolder, position: Int) {
        if (position < remainingAttempts) {
            holder.heart.setImageResource(R.drawable.ic_filled_heart_icon)
        } else {
            holder.heart.setImageResource(R.drawable.ic_empty_heart_icon)
        }
    }

    override fun getItemCount(): Int = heartCount

    fun updateRemainingAttempts(attempts: Int) {
        remainingAttempts = attempts
        notifyDataSetChanged()
    }
}