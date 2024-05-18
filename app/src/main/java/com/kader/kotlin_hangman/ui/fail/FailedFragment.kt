package com.kader.kotlin_hangman.ui.fail

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import com.kader.kotlin_hangman.R
import com.kader.kotlin_hangman.databinding.FragmentFailedBinding
import com.kader.kotlin_hangman.ui.BaseFragment
import com.kader.kotlin_hangman.ui.game.GameFragment
import com.kader.kotlin_hangman.util.ScreenName
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FailedFragment (override val screenName: String = ScreenName.FAILED_SCREEN) :
    BaseFragment<FragmentFailedBinding, FailedViewModel>() {
    override fun initView() {

        val correctWord = arguments?.getString("correctWord") ?: ""

        binding.correctWord.text = getString(R.string.correct_answer, correctWord)

        binding.buttonAgain.setOnClickListener{
            val gameFragment = GameFragment()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, gameFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

    }

    override val viewModel by viewModels<FailedViewModel>()

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentFailedBinding =FragmentFailedBinding.inflate(inflater,container,false)

}