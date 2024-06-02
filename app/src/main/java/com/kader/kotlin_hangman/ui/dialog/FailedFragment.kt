package com.kader.kotlin_hangman.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kader.kotlin_hangman.R
import com.kader.kotlin_hangman.databinding.FragmentFailedBinding
import com.kader.kotlin_hangman.ui.game.GameFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FailedFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentFailedBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFailedBinding.inflate(inflater, container, false)
        return binding.root
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
            val gameFragment = GameFragment()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, gameFragment)
            transaction.commit()
            dismiss()
        }
    }
}