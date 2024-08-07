package com.kader.kotlin_hangman.ui.game

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.kader.kotlin_hangman.R
import com.kader.kotlin_hangman.databinding.FragmentGameBinding
import com.kader.kotlin_hangman.ui.BaseFragment
import com.kader.kotlin_hangman.ui.adapter.AlphabetAdapter
import com.kader.kotlin_hangman.ui.adapter.HeartAdapter
import com.kader.kotlin_hangman.ui.dialog.FailedDialog
import com.kader.kotlin_hangman.ui.dialog.SuccessDialog
import com.kader.kotlin_hangman.util.ScreenName
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class GameFragment(override val screenName: String = ScreenName.GAME_SCREEN) :
    BaseFragment<FragmentGameBinding, GameViewModel>() {

    private lateinit var failedDialog: FailedDialog
    private lateinit var successDialog: SuccessDialog
    private lateinit var heartAdapter: HeartAdapter

    private var score = 0

    override fun initView() {

        setupAlphabetRecyclerView()

        viewModelInit()

        setupHeartRecyclerView()
    }

    private fun viewModelInit() {
        viewModel.selectedWord.observe(viewLifecycleOwner) { word ->
            binding.wordClue.text = "_ ".repeat(word?.length ?: 0)
        }

        viewModel.description.observe(viewLifecycleOwner) { description ->
            binding.wordClueDescription.text = getString(R.string.hint, description)
        }

        viewModel.level.observe(viewLifecycleOwner) { level ->
            binding.levelText.text = level.toString()
        }
    }

    private fun setupAlphabetRecyclerView() {
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
    }

    private fun handleAlphabetGridItemClick(alphabet: List<String>) {
        binding.gridViewAlphabet.setOnItemClickListener { _, gridViewItem, _, _ ->
            val position: Int = binding.gridViewAlphabet.getPositionForView(gridViewItem)

            val letter = alphabet.getOrNull(position)?.uppercase(Locale("tr", "TR"))

            if (letter != null && !viewModel.selectedWord.value.isNullOrEmpty()) {
                val selectedWord = viewModel.selectedWord.value!!.uppercase(Locale("tr", "TR"))

                if (selectedWord.contains(letter)) {
                    updateHiddenWord(letter)
                    gridViewItem.setBackgroundResource(R.drawable.custom_success_background)
                    score += 10
                } else {
                   viewModel.updateRemainingAttempts(viewModel.remainingAttempts.value!! - 1)
                    gridViewItem.setBackgroundResource(R.drawable.custom_failed_background)
                    binding.stepImage.setImageResource(getWrongImageResource())
                    score -= 5
                }

            } else {
                Log.e("FragmentGame", "Invalid position: $position")
            }
        }
    }

    private fun setupHeartRecyclerView() {
        heartAdapter = HeartAdapter(6)
        binding.recyclerHeart.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerHeart.adapter = heartAdapter
    }

    private fun getWrongImageResource(): Int {
        val remainingAttempts = viewModel.remainingAttempts.value ?: 0

        heartAdapter.updateRemainingAttempts(remainingAttempts)

        if (remainingAttempts == 0) {
            showFailedDialog()
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
                viewModel.addScore(score)
                showSuccessDialog()
            }
        }
    }

    private fun showFailedDialog() {
        failedDialog = FailedDialog()
        failedDialog.setOnRetryListener {
            findNavController().navigate(R.id.action_gameFragment_self)
        }
        val bundle = Bundle().apply {
            putString("correctWord", viewModel.selectedWord.value)
        }
        failedDialog.arguments = bundle
        failedDialog.show(parentFragmentManager, "failed_dialog")
    }

    private fun showSuccessDialog() {
        successDialog = SuccessDialog()
        successDialog.setOnNewGameListener {
            findNavController().navigate(R.id.action_gameFragment_self)
        }
        successDialog.show(parentFragmentManager, "success_dialog")
    }

    override val viewModel by viewModels<GameViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                AlertDialog.Builder(requireContext())
                    .setMessage(getString(R.string.are_you_sure_go__out))
                    .setPositiveButton(getString(R.string.yes)) { _, _ ->
                        isEnabled = false
                        requireActivity().onBackPressedDispatcher.onBackPressed()
                    }
                    .setNegativeButton(getString(R.string.no), null)
                    .show()
            }
        })
    }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentGameBinding = FragmentGameBinding.inflate(inflater, container, false)
}