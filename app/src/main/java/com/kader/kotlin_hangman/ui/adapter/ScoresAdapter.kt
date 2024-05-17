package com.kader.kotlin_hangman.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kader.kotlin_hangman.databinding.ItemScoreBinding
import com.kader.kotlin_hangman.entity.Score

class ScoresAdapter : RecyclerView.Adapter<ScoresAdapter.ScoresViewHolder>() {

    private val scores = mutableListOf<Score>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScoresViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemScoreBinding.inflate(inflater, parent, false)
        return ScoresViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ScoresViewHolder, position: Int) {
        holder.bind(scores[position])
    }

    override fun getItemCount(): Int = scores.size

    inner class ScoresViewHolder(private val binding: ItemScoreBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(score: Score) {
            binding.textViewScore.text = score.value.toString()
        }
    }

    fun setScores(newScores: List<Score>) {
        scores.clear()
        scores.addAll(newScores)
        notifyDataSetChanged()
    }
}