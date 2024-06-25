package com.kader.kotlin_hangman.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kader.kotlin_hangman.ui.BaseDialog
import com.kader.kotlin_hangman.R
import com.kader.kotlin_hangman.databinding.DialogFailedBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FailedDialog : BaseDialog<DialogFailedBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> DialogFailedBinding
        get() = DialogFailedBinding::inflate

    private var onRetryListener: (() -> Unit)? = null

    fun setOnRetryListener(listener: () -> Unit) {
        onRetryListener = listener
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showCorrectWord()
        buttonRepeatAction()
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
}