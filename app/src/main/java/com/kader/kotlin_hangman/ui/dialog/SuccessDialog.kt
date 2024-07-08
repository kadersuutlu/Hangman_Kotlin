package com.kader.kotlin_hangman.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kader.kotlin_hangman.R
import com.kader.kotlin_hangman.ui.BaseDialog
import com.kader.kotlin_hangman.databinding.DialogSuccessBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SuccessDialog : BaseDialog<DialogSuccessBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> DialogSuccessBinding
        get() = DialogSuccessBinding::inflate

    private var onNewGameListener: (() -> Unit)? = null

    @Inject
    lateinit var dialogViewModel: DialogViewModel

    fun setOnNewGameListener(listener: () -> Unit) {
        onNewGameListener = listener
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonSuccessAction()
        displayMaxAndLastScores()
    }

    private fun buttonSuccessAction() {
        binding.buttonSuccess.setOnClickListener {
            onNewGameListener?.invoke()
            dismiss()
        }
    }

    private fun displayMaxAndLastScores() {
        dialogViewModel.maxAndLastScores()
        dialogViewModel.maxScore.observe(viewLifecycleOwner) { maxScore ->
            binding.maxScore.text = if (maxScore != null) {
                getString(R.string.max_score_format, maxScore)
            } else {
                getString(R.string.no_scores_found)
            }
        }

        dialogViewModel.lastScore.observe(viewLifecycleOwner) { lastScore ->
            binding.score.text = if (lastScore != null) {
                getString(R.string.last_score_format, lastScore.value)
            } else {
                getString(R.string.no_scores_found)
            }
        }
    }
}