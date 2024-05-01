package com.kader.kotlin_hangman.ui.start

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.kader.kotlin_hangman.R
import com.kader.kotlin_hangman.databinding.FragmentStartBinding
import com.kader.kotlin_hangman.ui.BaseFragment
import com.kader.kotlin_hangman.util.ScreenName
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StartFragment(override val screenName: String = ScreenName.START_SCREEN) :
    BaseFragment<FragmentStartBinding, StartViewModel>() {
    override fun initView() {
        binding.startButton.setOnClickListener {
            findNavController().navigate(R.id.action_startFragment_to_gameFragment)
        }
        binding.scoreButton.setOnClickListener {
            findNavController().navigate(R.id.action_startFragment_to_scoresFragment)
        }
    }

    override val viewModel by viewModels<StartViewModel>()

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentStartBinding = FragmentStartBinding.inflate(inflater, container, false)
}
