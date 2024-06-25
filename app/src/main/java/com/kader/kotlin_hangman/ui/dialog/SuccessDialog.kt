package com.kader.kotlin_hangman.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kader.kotlin_hangman.ui.BaseDialog
import com.kader.kotlin_hangman.databinding.DialogSuccessBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SuccessDialog : BaseDialog<DialogSuccessBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> DialogSuccessBinding
        get() = DialogSuccessBinding::inflate

    private var onNewGameListener: (() -> Unit)? = null

    fun setOnNewGameListener(listener: () -> Unit) {
        onNewGameListener = listener
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonSuccessAction()
    }

    private fun buttonSuccessAction() {
        binding.buttonSuccess.setOnClickListener {
            onNewGameListener?.invoke()
            dismiss()
        }
    }
}