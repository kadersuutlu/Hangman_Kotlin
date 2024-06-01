package com.kader.kotlin_hangman.ui.game

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.kader.kotlin_hangman.R
import com.kader.kotlin_hangman.databinding.FragmentGameBinding
import com.kader.kotlin_hangman.ui.BaseFragment
import com.kader.kotlin_hangman.ui.adapter.AlphabetAdapter
import com.kader.kotlin_hangman.ui.fail.FailedFragment
import com.kader.kotlin_hangman.ui.success.SuccessFragment
import com.kader.kotlin_hangman.util.ScreenName
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class GameFragment(override val screenName: String = ScreenName.GAME_SCREEN) :
    BaseFragment<FragmentGameBinding, GameViewModel>() {

    private var remainingAttempts = 6
    private var incrementScore = 0

    private val selectedLetters = mutableListOf<String>()

    private val emptyHeartResource = R.drawable.ic_empty_heart_icon

    override fun initView() {

        val alphabet = listOf(
            "A",
            "B",
            "C",
            "Ç",
            "D",
            "E",
            "F",
            "G",
            "Ğ",
            "H",
            "I",
            "İ",
            "J",
            "K",
            "L",
            "M",
            "N",
            "O",
            "Ö",
            "P",
            "R",
            "S",
            "Ş",
            "T",
            "U",
            "Ü",
            "V",
            "Y",
            "Z"
        )
        val adapter = AlphabetAdapter(requireContext(), alphabet)
        binding.gridViewAlphabet.adapter = adapter

        handleAlphabetGridItemClick(alphabet)

        viewModel.selectedWord.observe(viewLifecycleOwner) { word ->
            binding.wordClue.text = "_ ".repeat(word?.length ?: 0)
        }

        viewModel.description.observe(viewLifecycleOwner) { description ->
            binding.wordClueDescription.text = getString(R.string.hint, description)
        }

        viewModel.init()
    }

    private fun handleAlphabetGridItemClick(alphabet: List<String>) {
        binding.gridViewAlphabet.setOnItemClickListener { _, gridViewItem, _, _ ->
            val position: Int = binding.gridViewAlphabet.getPositionForView(gridViewItem)

            val letter = alphabet.getOrNull(position)?.toUpperCase(Locale.ROOT)

            if (letter != null && !selectedLetters.contains(letter)) {
                selectedLetters.add(letter)

                if (viewModel.selectedWord.value?.toUpperCase(Locale.ROOT)
                        ?.contains(letter) == true
                ) {
                    updateHiddenWord(letter)
                    gridViewItem.setBackgroundResource(R.drawable.custom_success_background)
                    incrementScore += 10
                    if (!binding.wordClue.text.contains("_")) {
                        showSuccessFragment()
                    }
                } else {
                    remainingAttempts--
                    gridViewItem.setBackgroundResource(R.drawable.custom_failed_background)
                    binding.stepImage.setImageResource(getWrongImageResource())
                    incrementScore -= 5

                    if (remainingAttempts == 0) {
                        showFailedFragment()
                    }
                }

            } else {
                Log.e("FragmentGame", "Invalid position: $position")
            }
        }
        viewModel.incrementScore(incrementScore)
    }


    private fun getWrongImageResource(): Int {
        when (remainingAttempts) {
            5 -> binding.heart6.setImageResource(emptyHeartResource)
            4 -> binding.heart5.setImageResource(emptyHeartResource)
            3 -> binding.heart4.setImageResource(emptyHeartResource)
            2 -> binding.heart3.setImageResource(emptyHeartResource)
            1 -> binding.heart2.setImageResource(emptyHeartResource)
            0 -> binding.heart1.setImageResource(emptyHeartResource)
        }

        if (remainingAttempts == 0) {
            showFailedFragment()
        }

        return when (remainingAttempts) {
            5 -> R.drawable.step2_icon
            4 -> R.drawable.step3_icon
            3 -> R.drawable.step4_icon
            2 -> R.drawable.step5_icon
            1 -> R.drawable.step6_icon
            0 -> R.drawable.step7_icon
            else -> R.drawable.step1_icon
        }
    }

    private fun updateHiddenWord(letter: String) {
        val updatedWord = StringBuilder(binding.wordClue.text.toString())

        val indicesToUpdate = mutableListOf<Int>()

        viewModel.selectedWord.value?.indices?.forEach { index ->
            if (viewModel.selectedWord.value?.get(index).toString()
                    .equals(letter, ignoreCase = true)
            ) {
                indicesToUpdate.add(index)
            }
        }

        if (indicesToUpdate.isNotEmpty()) {
            indicesToUpdate.forEach { index ->
                if (index * 2 < updatedWord.length) {
                    updatedWord.setCharAt(index * 2, letter[0])
                } else {
                    Log.e(
                        "FragmentGame",
                        "Invalid index: $index, updatedWord length: ${updatedWord.length}"
                    )
                }
            }

            binding.wordClue.text = updatedWord.toString()
            Log.d("FragmentGame", "Updated word: $updatedWord")

            if (updatedWord.indexOf('_') == -1) {
                showSuccessFragment()
            }
        }
    }

    private fun showFailedFragment() {
        val failedFragment = FailedFragment()
        val bundle = Bundle()
        bundle.putString("correctWord", viewModel.selectedWord.value)
        failedFragment.arguments = bundle
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, failedFragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun showSuccessFragment() {
        val successFragment = SuccessFragment()
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, successFragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override val viewModel by viewModels<GameViewModel>()

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentGameBinding = FragmentGameBinding.inflate(inflater, container, false)
}

