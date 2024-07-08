package com.kader.kotlin_hangman.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kader.kotlin_hangman.ui.BaseDialog
import com.kader.kotlin_hangman.R
import com.kader.kotlin_hangman.databinding.DialogFailedBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FailedDialog : BaseDialog<DialogFailedBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> DialogFailedBinding
        get() = DialogFailedBinding::inflate

    private var onRetryListener: (() -> Unit)? = null

    @Inject
    lateinit var dialogViewModel: DialogViewModel

    fun setOnRetryListener(listener: () -> Unit) {
        onRetryListener = listener
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showCorrectWord()
        buttonRepeatAction()
        displayMaxAndLastScores()
    }

    private fun showCorrectWord() {
        val correctWord = arguments?.getString("correctWord") ?: ""
        binding.correctWord.text = getString(R.string.correct_answer, correctWord)
    }

    private fun buttonRepeatAction() {
        binding.buttonAgain.setOnClickListener {
            onRetryListener?.invoke()
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